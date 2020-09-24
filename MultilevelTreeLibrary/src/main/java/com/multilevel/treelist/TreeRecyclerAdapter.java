package com.multilevel.treelist;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangke on 2017-1-14.
 */
public abstract class TreeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 存储所有可见的Node
     */
    protected List<Node> mNodes;

    /**
     * 存储所有的Node
     */
    protected List<Node> mAllNodes;

    /**
     * 点击的回调接口
     */
    private OnTreeNodeClickListener onTreeNodeClickListener;
    /**
     * 默认不展开
     */
    private int defaultExpandLevel;

    /**
     * 展开与关闭的图片
     */
    private int iconExpand, iconNoExpand;

    public void setOnTreeNodeClickListener(
            OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    public TreeRecyclerAdapter(List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        this.iconExpand = iconExpand;
        this.iconNoExpand = iconNoExpand;

        for (Node node : datas) {
            node.getChildren().clear();
            node.iconExpand = iconExpand;
            node.iconNoExpand = iconNoExpand;
        }
        this.defaultExpandLevel = defaultExpandLevel;
        /**
         * 对所有的Node进行排序
         */
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        /**
         * 过滤出可见的Node
         */
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Node node = mNodes.get(position);

        holder.itemView.setPadding(node.getLevel() * 50, 3, 3, 3);

        /**
         * 设置节点点击时，可以展开以及关闭,将事件继续往外公布
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Node currentNode = mNodes.get(position);
                if (currentNode != null && !currentNode.isLeaf()) {
                    if (currentNode.isExpand()) {
                        currentNode.setExpand(false);
                        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
                        notifyDataSetChanged();// 刷新视图
                    } else {
                        for (int i = 0; i < mNodes.size(); i++) {
                            Node node = mNodes.get(i);
                            if ((node.getParent() == null && currentNode.getParent() == null) ||
                                    (node.getParent() != null && currentNode.getParent() != null &&
                                            node.getParent().equals(currentNode.getParent()))) {
                                if (!node.isLeaf()) {
                                    if (i == position) {
                                        node.setExpand(true);
                                    } else {
                                        node.setExpand(false);
                                    }
                                }
                            }
                        }
                        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
                        notifyDataSetChanged();// 刷新视图
                    }
                }
                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(mNodes.get(position),
                            position);
                }
            }
        });
        onBindViewHolder(node, holder, position);
    }

    @Override
    public int getItemCount() {
        return mNodes.size();
    }

    /**
     * 清除掉之前数据并刷新  重新添加
     *
     * @param mlists
     * @param defaultExpandLevel 默认展开几级列表
     */
    public void addDataAll(List<Node> mlists, int defaultExpandLevel) {
        mAllNodes.clear();
        addData(-1, mlists, defaultExpandLevel);
    }

    /**
     * 在指定位置添加数据并刷新 可指定刷新后显示层级
     *
     * @param index
     * @param mlists
     * @param defaultExpandLevel 默认展开几级列表
     */
    public void addData(int index, List<Node> mlists, int defaultExpandLevel) {
        this.defaultExpandLevel = defaultExpandLevel;
        notifyData(index, mlists);
    }

    /**
     * 在指定位置添加数据并刷新
     *
     * @param index
     * @param mlists
     */
    public void addData(int index, List<Node> mlists) {
        notifyData(index, mlists);
    }

    /**
     * 添加数据并刷新
     *
     * @param mlists
     */
    public void addData(List<Node> mlists) {
        addData(mlists, defaultExpandLevel);
    }

    /**
     * 添加数据并刷新 可指定刷新后显示层级
     *
     * @param mlists
     * @param defaultExpandLevel
     */
    public void addData(List<Node> mlists, int defaultExpandLevel) {
        this.defaultExpandLevel = defaultExpandLevel;
        notifyData(-1, mlists);
    }

    /**
     * 添加数据并刷新
     *
     * @param node
     */
    public void addData(Node node) {
        addData(node, defaultExpandLevel);
    }

    /**
     * 添加数据并刷新 可指定刷新后显示层级
     *
     * @param node
     * @param defaultExpandLevel
     */
    public void addData(Node node, int defaultExpandLevel) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);
        this.defaultExpandLevel = defaultExpandLevel;
        notifyData(-1, nodes);
    }

    /**
     * 移除node
     *
     * @param node
     */
    public void removeData(Node node) {
        if (node == null) {
            return;
        }
        removeDeleteNode(node);
        for (Node n : mAllNodes) {
            n.getChildren().clear();
        }
        mAllNodes = TreeHelper.getSortedNodes(mAllNodes, defaultExpandLevel);
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        //刷新数据
        notifyDataSetChanged();
    }

    /**
     * 批量移除node
     *
     * @param nodes
     */
    public void removeData(List<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        for (Node node : nodes) {
            removeDeleteNode(node);
        }
        for (Node n : mAllNodes) {
            n.getChildren().clear();
        }
        mAllNodes = TreeHelper.getSortedNodes(mAllNodes, defaultExpandLevel);
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        //刷新数据
        notifyDataSetChanged();
    }

    private void removeDeleteNode(Node node) {
        if (node == null) {
            return;
        }
        List<Node> childrens = node.getChildren();
        if (childrens != null && !childrens.isEmpty()) {
            for (Node n : childrens) {
                removeDeleteNode(n);
            }
        }
        mAllNodes.remove(node);
    }

    /**
     * 刷新数据
     *
     * @param index
     * @param mListNodes
     */
    private void notifyData(int index, List<Node> mListNodes) {
        for (int i = 0; i < mListNodes.size(); i++) {
            Node node = mListNodes.get(i);
            node.getChildren().clear();
            node.iconExpand = iconExpand;
            node.iconNoExpand = iconNoExpand;
        }
        for (int i = 0; i < mAllNodes.size(); i++) {
            Node node = mAllNodes.get(i);
            node.getChildren().clear();
            node.isNewAdd = false;
        }
        if (index != -1) {
            mAllNodes.addAll(index, mListNodes);
        } else {
            mAllNodes.addAll(mListNodes);
        }
        /**
         * 对所有的Node进行排序
         */
        mAllNodes = TreeHelper.getSortedNodes(mAllNodes, defaultExpandLevel);
        /**
         * 过滤出可见的Node
         */
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        //刷新数据
        notifyDataSetChanged();
    }

    /**
     * 获取排序后所有节点
     *
     * @return
     */
    public List<Node> getAllNodes() {
        if (mAllNodes == null)
            mAllNodes = new ArrayList<Node>();
        return mAllNodes;
    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        Node n = mNodes.get(position);

        if (n != null) {// 排除传入参数错误异常
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand());
                mNodes = TreeHelper.filterVisibleNode(mAllNodes);
                notifyDataSetChanged();// 刷新视图
            }
        }
    }

    /**
     * 设置多选
     *
     * @param node
     * @param checked
     */
    protected void setChecked(final Node node, boolean checked) {
        node.setChecked(checked);
        setChildChecked(node, checked);
        if (node.getParent() != null)
            setNodeParentChecked(node.getParent(), checked);
        notifyDataSetChanged();
    }

    /**
     * 设置是否选中
     *
     * @param node
     * @param checked
     */
    public <T, B> void setChildChecked(Node<T, B> node, boolean checked) {
        if (!node.isLeaf()) {
            node.setChecked(checked);
            for (Node childrenNode : node.getChildren()) {
                setChildChecked(childrenNode, checked);
            }
        } else {
            node.setChecked(checked);
        }
    }

    private void setNodeParentChecked(Node node, boolean checked) {
        if (checked) {
            node.setChecked(checked);
            if (node.getParent() != null)
                setNodeParentChecked(node.getParent(), checked);
        } else {
            List<Node> childrens = node.getChildren();
            boolean isChecked = false;
            for (Node children : childrens) {
                if (children.isChecked()) {
                    isChecked = true;
                }
            }
            //如果所有自节点都没有被选中 父节点也不选中
            if (!isChecked) {
                node.setChecked(checked);
            }
            if (node.getParent() != null)
                setNodeParentChecked(node.getParent(), checked);
        }
    }

    public abstract void onBindViewHolder(Node node, RecyclerView.ViewHolder holder, final int position);
}