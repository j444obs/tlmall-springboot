package fun.sherman.tlmall.domain;

import java.util.Objects;

/**
 * 商品分类
 */
public class Category {

    private Integer id;
    /**
     * 商品父类别id：id=0表示根节点，一级类别
     */
    private Integer parentId;
    private String name;
    /**
     * 类别装填：1-正常，2-已废弃
     */
    private Integer status;
    /**
     * 排序编号，同类展示顺序，数值相等则自然排序
     */
    private Integer sortOrder;
    private java.util.Date createTime;
    private java.util.Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }


    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }


    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(parentId, category.parentId) &&
                Objects.equals(name, category.name) &&
                Objects.equals(status, category.status) &&
                Objects.equals(sortOrder, category.sortOrder) &&
                Objects.equals(createTime, category.createTime) &&
                Objects.equals(updateTime, category.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, name, status, sortOrder, createTime, updateTime);
    }
}
