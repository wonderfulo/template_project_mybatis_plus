package com.cxy.utils.executor;

import com.cxy.util.NumberUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ExecutorExample.java
 * @Description 多线程优化查询实例
 * @createTime 2020年12月15日 14:07:00
 */
public class ExecutorExample {



//    超导68期，数据查询过慢，使用多线程优化查询
//    /**
//     * 根据用户名查询
//     * 使用多线程优化查询
//     * @param inSql   查验SQL
//     * @param objects 绑定参数
//     * @param pg      分页信息
//     * @return List 查询结果集
//     * @throws DAOException
//     */
//    public List queryBySqlAndMultiThread(String inSql, Object[] objects, Page page)
//            throws DAOException {
//        try {
//            final String sqlStr = inSql;
//            final Page pg = page;
//            final Object[] objs = objects;
//            ExecutorService executorService = Executors.newFixedThreadPool(6);
//            List<FutureTask<Object>> futureTasks = new ArrayList<>();
//
//            futureTasks.add(new FutureTask<>(new Callable<Object>() {
//                @Override
//                public Integer call() {
//                    // 线程1执行程序
//                    String sql = "select count(1) " + sqlStr.substring(sqlStr.indexOf(" from "));
//                    List list = queryBySql(sql, objs);
//                    /**
//                     * 改成这样的原因是当list为空时list.listIterator().next()就会报错java.util.NoSuchElementException
//                     * 20151231 zhjtao
//                     */
//                    Long count = 0L;
//                    if(list != null && list.size()>0){
//                        count = NumberUtils.bigint2long(list.listIterator().next());
//                    }
//                    return count.intValue();
//                }
//            }));
//
//            futureTasks.add(new FutureTask<>(new Callable<Object>() {
//                @Override
//                public List call() {
//                    List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//                        public Object doInHibernate(Session session)
//                                throws HibernateException, SQLException {
//                            Query query = session.createSQLQuery(sqlStr);
//                            prepareQuery(query);
//                            for (int i = 0; i < objs.length; i++) {
//                                query.setParameter(i, objs[i]);
//                            }
//                            query.setFirstResult(
//                                    (pg.getCurrentPage() - 1) * pg.getPageSize())
//                                    .setMaxResults(pg.getPageSize());
//                            return query.list();
//                        }
//                    });
//                    return list;
//                }
//            }));
//
//            // 加入 线程池
//            for (FutureTask<Object> futureTask : futureTasks) {
//                executorService.submit(futureTask);
//            }
//
//            // 获得任务
//            FutureTask<Object> objectFutureTask = futureTasks.get(0);
//            FutureTask<Object> objectFutureTask1 = futureTasks.get(1);
//
//            int count = (int)objectFutureTask.get();
//            page.setTotalRows(count);
//            Object o = objectFutureTask1.get();
//            return (List) o;
//        } catch (Exception e) {
//            log.error(e);
//            throw new DAOException(e);
//        }
//    }



    class Page {
        /*
         * 总记录数
         */
        private int totalRows;
        /*
         * 总页数
         */
        private int totalPages;
        /*
         * 每页显示记录数
         */
        private int pageSize = 10;
        /*
         * 当前页数
         */
        private int currentPage = 1;

        public Page() {
        }

        public Page(int currentPage, int pageSize) {
            this.currentPage = currentPage;
            this.pageSize = pageSize;
        }

        public int getTotalPages() {
            return totalPages;
        }
        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalRows() {
            return totalRows;
        }
        public void setTotalRows(int totalRows) {
            this.totalRows = totalRows;
            if(pageSize>0) {
                this.totalPages = totalRows/pageSize;
                int mod = totalRows % pageSize;
                if (mod > 0) {
                    totalPages++;
                }
            }
        }
        public int getPageSize() {
            return pageSize;
        }
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
        public int getCurrentPage() {
            return currentPage;
        }
        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        @Override
        public String toString() {
            return "Page{" +
                    "totalRows=" + totalRows +
                    ", totalPages=" + totalPages +
                    ", pageSize=" + pageSize +
                    ", currentPage=" + currentPage +
                    '}';
        }
    }


    class DAOException extends RuntimeException {

        /**
         *
         */
        private static final long serialVersionUID = 2573102893349117477L;
        /*
         * 错误码
         */
        private String errCode;
        /*
         * 错误信息
         */
        private String errMsg;
        /*
         * 修复信息
         */
        private String fixMsg;

        public String getErrCode() {
            return errCode;
        }
        public void setErrCode(String errCode) {
            this.errCode = errCode;
        }
        public String getErrMsg() {
            return errMsg;
        }
        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }
        public DAOException(Throwable e) {
            super(e);
        }
        public DAOException(String errCode) {
            this.errCode = errCode;
        }
        public DAOException(String errCode, Throwable e) {
            super(e);
            this.errCode = errCode;
        }
        public String getFixMsg() {
            return fixMsg;
        }
        public void setFixMsg(String fixMsg) {
            this.fixMsg = fixMsg;
        }

    }
}
