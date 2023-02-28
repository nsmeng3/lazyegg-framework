package io.lazyegg.core.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TreeNode
 * 树节点
 *
 * @author DifferentW  nsmeng3@163.com
 */

public class TreeNode<T, E extends Map<String, Object>> {

    /**
     * 节点Id
     */
    private T id;

    /**
     * 父节点Id
     */
    private T parentId;

    /**
     * 节点名称
     */
    private String label = "";

    /**
     * 子节点集合
     */
    private List<TreeNode<T, E>> children = new ArrayList<>();

    /**
     * 扩展数据对象
     */
    private E ext;

    public TreeNode(T id, T parentId, String label) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public T getParentId() {
        return parentId;
    }

    public void setParentId(T parentId) {
        this.parentId = parentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeNode<T, E>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T, E>> children) {
        this.children = children;
    }

    public E getExt() {
        return ext;
    }

    public TreeNode<T, E> setExt(E ext) {
        this.ext = ext;
        return this;
    }
}

