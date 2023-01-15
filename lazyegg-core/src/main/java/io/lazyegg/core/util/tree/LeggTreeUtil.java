package io.lazyegg.core.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TreeUtil
 *
 * @author DifferentW nsmeng3@163.com
 */
public class LeggTreeUtil<T, E extends Map<String, Object>> {

    // 保存参与构建树形的所有数据（通常数据库查询结果）
    private final List<TreeNode<T, E>> nodeList;
    private final T rootNodeId;

    /**
     * 构造方法
     *
     * @param nodeList 待构造数据集合
     * @param rootNodeId 根节点id
     */
    public LeggTreeUtil(List<TreeNode<T, E>> nodeList, T rootNodeId) {
        this.nodeList = nodeList;
        this.rootNodeId = rootNodeId;
    }

    /**
     * 获取所有根节点
     *
     * @return 所有根节点集合
     */
    private List<TreeNode<T, E>> getRootNode() {
        // 保存所有根节点（所有根节点的数据）
        List<TreeNode<T, E>> rootNodeList = new ArrayList<>();
        // treeNode：查询出的每一条数据（节点）
        for (TreeNode<T, E> treeNode : nodeList) {
            // 判断当前节点是否为根节点，此处注意：若parentId类型是String，则要采用equals()方法判断。
            if (rootNodeId == null ? (null == treeNode.getParentId()) : rootNodeId.equals(treeNode.getParentId())) {
                // 是，添加
                rootNodeList.add(treeNode);
            }
        }
        return rootNodeList;
    }

    /**
     * 为每一个根节点构造树形结构
     *
     * @return
     */
    public List<TreeNode<T, E>> buildTree() {
        // treeNodes：保存一个顶级节点所构建出来的完整树形
        List<TreeNode<T, E>> treeNodes = new ArrayList<>();
        // getRootNode()：获取所有的根节点
        for (TreeNode<T, E> treeRootNode : getRootNode()) {
            // 将顶级节点进行构建子树
            treeRootNode = buildChildTree(treeRootNode);
            // 完成一个顶级节点所构建的树形，增加进来
            treeNodes.add(treeRootNode);
        }
        return treeNodes;
    }

    /**
     * 递归 构建子树形结构
     *
     * @param pNode 父节点
     * @return 整棵树
     */
    private TreeNode<T, E> buildChildTree(TreeNode<T, E> pNode) {
        List<TreeNode<T, E>> childTree = new ArrayList<TreeNode<T, E>>();
        for (TreeNode<T, E> treeNode : nodeList) {
            // 防止id=pid异常数据导致死循环
            if (treeNode.getParentId() == null ? null == treeNode.getId() : treeNode.getParentId().equals(treeNode.getId())) {
                continue;
            }
            // 判断当前节点的pid是否等于父节点的id，即当前节点为其下的子节点
            if (treeNode.getParentId().equals(pNode.getId())) {
                // 再递归进行判断当前节点的情况，调用自身方法
                childTree.add(buildChildTree(treeNode));
            }
        }
        // for循环结束，即节点下没有任何节点，树形构建结束，设置树结果
        pNode.setChildren(childTree);
        return pNode;
    }


    /**
     * 根据节点id查找节点
     *
     * @param treeNodes 树形结构实体集合
     * @param id 查找节点id
     * @return
     */
    public List<TreeNode<T, E>> findChildNode(List<TreeNode<T, E>> treeNodes, T id) {
        List<TreeNode<T, E>> nodes = new ArrayList<>();
        findChildNode(treeNodes, id, false, nodes);
        return nodes;
    }

    /**
     * 查找子节点
     *
     * @param treeNode 树形结构实体
     * @param id 查找节点id
     * @return
     */
    public List<TreeNode<T, E>> findChildNode(TreeNode<T, E> treeNode, T id) {
        List<TreeNode<T, E>> nodes = new ArrayList<>();
        ArrayList<TreeNode<T, E>> treeNodes = new ArrayList<>();
        treeNodes.add(treeNode);
        findChildNode(treeNodes, id, false, nodes);
        return nodes;
    }

    /**
     * 查找子节点
     *
     * @param treeNodes 树形结构实体集合
     * @param id 查找节点id
     * @param b 辅助判断标识
     * @param nodes 返回值集合
     */
    private void findChildNode(List<TreeNode<T, E>> treeNodes, T id, boolean b, List<TreeNode<T, E>> nodes) {
        for (TreeNode<T, E> treeNode : treeNodes) {
            if (b || id == treeNode.getId()) {
                nodes.add(treeNode);
                List<TreeNode<T, E>> children = treeNode.getChildren();
                if (!children.isEmpty()) {
                    findChildNode(children, null, true, nodes);
                }

            } else {
                findChildNode(treeNode.getChildren(), id, false, nodes);
            }
        }
    }

}
