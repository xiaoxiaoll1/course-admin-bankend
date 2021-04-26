package service;

import dto.page.QueryPageDto;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * @author xiaozj
 */
public interface BaseService<T, ID extends Serializable> {

    /**
     * 保存(不区分新增/更新)
     *
     * @param entity 单个实例
     * @return T
     */
    T save(T entity);

    /**
     * 保存并刷新
     *
     * @param entity 单个实例
     * @return T
     */
    T saveAndFlush(T entity);

    /**
     * 通过id查找实例
     *
     * @param id
     * @return
     */
    T findById(ID id);

    /**
     * 仅更新实例
     *
     * @param entity 单个实例
     * @return T
     * @throws Exception 异常
     */
    T update(T entity) throws Exception;

    /**
     * 删除单个实例
     *
     * @param entity 实例
     */
    void deleteEntity(T entity);

    /**
     * 删除单个实例
     *
     * @param id 实例ID
     */
    void deleteById(ID id);

    /**
     * 删除多个实例
     *
     * @param entities 多个实例
     */
    void deleteBatch(List<T> entities);

    /**
     * 查找全部
     *
     * @return List<T>
     */
    List<T> findAll();

    /**
     * 分页查询
     * @param dto
     * @return
     */
    Page<T> queryPage(QueryPageDto dto);

    /**
     * 根据条件分页查询
     *
     * @param dto
     * @param entity
     * @return
     */
    Page<T> queryPageWithCondition(QueryPageDto dto, T entity);

    /**
     * 实例转化为查询条件
     *
     * @param entity 实例
     * @return Specification<T>
     */
    Specification<T> getSpecification(T entity);

    /**
     * 使用反射特性取出实例的属性, 再构建springJpa的断言表达式
     * @param entity
     * @param root
     * @param criteriaQuery
     * @param criteriaBuilder
     * @return
     */
    List<Predicate> getPredicateList(T entity, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder);
}
