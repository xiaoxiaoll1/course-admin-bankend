package service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.google.common.collect.Lists;
import dao.BaseJpaRepository;
import dto.page.QueryPageDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.BaseService;
import util.GenericClassUtils;

import javax.persistence.Id;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.List;


/**
 * 查询基类
 * 该类封装了常用的分页查询方法
 * @author xiaozj
 */
@Slf4j
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    protected Class<T> entityClass = (Class<T>) GenericClassUtils.getSuperClassGenericType(getClass(), 0);

    protected Class<ID> idClass = (Class<ID>) GenericClassUtils.getSuperClassGenericType(getClass(), 1);

    protected BaseJpaRepository<T, ID> baseJpaRepository;

    public BaseServiceImpl(BaseJpaRepository baseJpaRepository) {
        this.baseJpaRepository = baseJpaRepository;
    }


    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public T save(T entity) {
        return baseJpaRepository.save(entity);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public T saveAndFlush(T entity) {
        return baseJpaRepository.saveAndFlush(entity);
    }

    @Override
    public T findById(ID id) {
        T optional = null;
        // ID类型转换
        if (idClass.isInstance(id)) {
            optional = baseJpaRepository.getOne(idClass.cast(id));
        } else {
            switch (idClass.getName()) {
                case "java.lang.String":
                    optional = baseJpaRepository.getOne(idClass.cast(String.valueOf(id)));
                    break;
                case "java.lang.Integer":
                    optional = baseJpaRepository.getOne(idClass.cast(Integer.valueOf(id.toString())));
                    break;
                case "java.lang.Long":
                    optional = baseJpaRepository.getOne(idClass.cast(Long.valueOf(id.toString())));
                    break;
                default:
                    break;
            }
        }
        if (null != optional) {
            return optional;
        }
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public T update(T entity) throws Exception {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field f : fields) {
            Id id = f.getAnnotation(Id.class);
            if (null != id) {
                f.setAccessible(true);
                Object object = f.get(entity);
                if (null == object) {
                    throw new Exception("not find entity");
                }
                break;
            }
        }
        return baseJpaRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteEntity(T entity) {
        baseJpaRepository.delete(entity);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteBatch(List<T> entities) {
        baseJpaRepository.deleteInBatch(entities);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteById(ID id) {
        //ID类型转换
        if (idClass.isInstance(id)) {
            id = idClass.cast(id);
        } else {
            switch (idClass.getName()) {
                case "java.lang.String":
                    id = idClass.cast(String.valueOf(id));
                    break;
                case "java.lang.Integer":
                    id = idClass.cast(Integer.valueOf(id.toString()));
                    break;
                case "java.lang.Long":
                    id = idClass.cast(Long.valueOf(id.toString()));
                    break;
                default:
                    break;
            }
        }
        baseJpaRepository.deleteById(id);
    }

    @Override
    public List<T> findAll() {
        return baseJpaRepository.findAll();
    }

    @Override
    public Page<T> queryPage(QueryPageDto dto) {
        if (null == dto) {
            dto = new QueryPageDto();
        }
        Pageable pageable = getPageable(dto);
        return baseJpaRepository.findAll(pageable);
    }

    @Override
    public Page<T> queryPageWithCondition(QueryPageDto dto, T entity) {
        Pageable pageable = getPageable(dto);
        Specification<T> specification = getSpecification(entity);
        return baseJpaRepository.findAll(specification, pageable);
    }

    @Override
    public Specification<T> getSpecification(T entity) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = getPredicateList(entity, root, criteriaQuery, criteriaBuilder);
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @Override
    public List<Predicate> getPredicateList(T entity, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = Lists.newArrayList();
        if (entity != null) {
            Method[] methods = entity.getClass().getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !"getClass".equals(methodName)) {
                    String fileName = String.valueOf(methodName.charAt(3)).toLowerCase() + methodName.substring(4);
                    Object result = null;
                    try {
                        result = method.invoke(entity);
                    } catch (Exception e) {
                        log.error("entity method invoke error", e);
                    }
                    if (null != result) {
                        // 查询结果时经过此逻辑
                        if (result instanceof String) {
                            String strResult = (String) result;
                            // 默认like查询
                            Path path = root.get(fileName);
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(fileName))
                                    , "%" + strResult.toLowerCase() + "%"));
                        } else {
                            predicates.add(criteriaBuilder.equal(root.get(fileName), result));
                        }
                    }
                }
            }
        }
        return predicates;
    }


    private Pageable getPageable(QueryPageDto dto) {
        Sort sort = null;
        if (StringUtils.isNotBlank(dto.getOrderByStr())) {
            dto.parseOrderByStr(dto.getOrderByStr());
            sort = dto.getOrderBy();
        }
        int page = 0;
        if (dto.getCurrent() != 0) {
            page = dto.getCurrent() - 1;
        }
        Pageable pageable = (sort == null) ? PageRequest.of(page, dto.getPageSize()) :
                PageRequest.of(page, dto.getPageSize(), sort);
        return pageable;
    }
}
