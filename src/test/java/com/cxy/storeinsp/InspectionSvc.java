//package com.xxy.om.storeinsp.services;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.write.metadata.WriteSheet;
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.vsvz.model.UserInfo;
//import com.xxy.back.common.services.ShopGuideService;
//import com.xxy.back.common.utils.NumberUtils;
//import com.xxy.om.storeinsp.constant.ItemType;
//import com.xxy.om.storeinsp.controller.InspTemplateCtl;
//import com.xxy.om.storeinsp.entity.*;
//import com.xxy.om.storeinsp.excel.InspectionExportExcelDtoWriteHandler;
//import com.xxy.om.storeinsp.excel.InspectionExportExcelDto;
//import com.xxy.om.storeinsp.model.InspectionExportDto;
//import com.xxy.om.storeinsp.model.InspectionGroupDetailEntity;
//import com.xxy.om.storeinsp.model.UserPosition;
//import com.xxy.om.storeinsp.util.*;
//import com.xxy.om.storeinsp.util.excel.*;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.ratel.core.persistence.PageResult;
//import org.ratel.core.persistence.jpa.JPAEntityManager;
//import org.ratel.exception.RatelRuntimeException;
//import org.ratel.metadata.document.RatelDoc;
//import org.ratel.utils.*;
//import org.ratel.utils.data.DataTable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Field;
//import java.net.URLEncoder;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// *
// * @author byron
// *
// */
//@Service(value = "com.xxy.om.storeinsp.inspectionsvc")
//public class InspectionSvc {
//
//    /**
//     * 巡店记录保存。
//     *
//     * @param insp
//     * @throws Exception
//     */
//    public InspectionEntity inspectSave(String accessToken, UserInfo user, InspectionEntity insp) throws Exception {
//        // check
//        if (PrimitiveUtils.isNullString(insp.templateId))
//            throw new RatelRuntimeException("请指定巡检表模板");
//
//        InspTemplateSvc svc = InspTemplateCtl.getService();
//        TemplateEntity template = svc.templateDetail(user, insp.templateId);
//        // Map<String, Integer> answerScore = buildChoiceScoreMap(template);
//        // inspectSave_Check(user, insp, template, answerScore);
//        Map<String, jobTypeInfo> answerScore = buildElementScoreMap(template);
//        inspectSave_Check(user, insp, template);
//
//        // save
//        // if (insp.id == null || insp.id.isEmpty() || insp.id.length() < 36) {
//        insp.id = insp.createNewId();
//        // }else {
//        // String sql = "delete from om_si_template where id=?";
//        // List<Object> paraValues = new ArrayList<>();
//        // paraValues.add(insp.id);
//        // JPAHelper.nativeExecute(sql, paraValues);
//        // sql = "delete from om_si_template_element_group where template_id=?";
//        // JPAHelper.nativeExecute(sql, paraValues);
//        // sql = "delete from om_si_template_element where template_id=?";
//        // JPAHelper.nativeExecute(sql, paraValues);
//        // sql = "delete from om_si_template_choice where template_id=?";
//        // JPAHelper.nativeExecute(sql, paraValues);
//        // }
//        insp.inspectorId = user.getSysUserId();
//        insp.inspectorName = user.getName();
//
//        List<InspectionDetailEntity> details = insp.details;
//        List<InspectionWatcherEntity> watchers = insp.watchers;
//        if (details == null || details.isEmpty())
//            throw new RatelRuntimeException("请传入巡检明细！");
//        //一对多处理(巡店报告接收人 多人处理)
//        if(StringUtils.isNotBlank(insp.receiverId)){
//            String receiverId=insp.receiverId;
//            String receiverName=insp.receiverName;
//            String sql  = " INSERT INTO om_si_report_receiver ( id, insp_id,inviter, receiver_id, receiver_name, create_time, create_user ,";
//            sql += " update_time, update_user, delete_flag, app_id ) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
//            List<List<Object>> sqlPara = new ArrayList<>();
//            String id = insp.createNewId();
//            String inspId = insp.id;
//            String inviter=insp.inspectorId.toString();
//            Date createTime=new Date();
//            Long createUser=user.getSysUserId();
//            Date updateTime=new Date();
//            Long updateUser=user.getSysUserId();
//            int deleteFlag=0;
//            String appId=user.getAppId();
//
//            List<Object> paras = new ArrayList<>();
//            paras.add(id);
//            paras.add(inspId);
//            paras.add(inviter);
//            paras.add(receiverId);
//            paras.add(receiverName);
//            paras.add(createTime);
//            paras.add(createUser);
//            paras.add(updateTime);
//            paras.add(updateUser);
//            paras.add(deleteFlag);
//            paras.add(appId);
//            sqlPara.add(paras);
//            JPAHelper.nativeExecutePrepareBatch(sql, sqlPara);
//
//        }
//        Integer issueCount = 0;
//        Float totalScore = 0f;
//        List<assignJob> job = new ArrayList<>();
//        for (InspectionDetailEntity detail : details) {
//            detail.id = detail.createNewId();
//            detail.inspectionId = insp.id;
//            // detail.score = getChoiceScore(answerScore, detail.choiceId);
//            jobTypeInfo typeInfo = getChoiceScore(answerScore, detail.inspElementId);
//            detail.templateScore = typeInfo.score;
//
//            // 增加保存冗余信息
//            detail.inspElementName = typeInfo.eleName;
//            detail.inspElementDesc = typeInfo.eleDesc;
//            detail.groupId = typeInfo.groupId;
//            detail.groupName = typeInfo.groupName;
//            detail.groupShowOrder = typeInfo.groupOrder;
//            detail.elementShowOrder = typeInfo.eleOrder;
//
//            if (!detail.isAdaptation) {
//                detail.score = null;
//            }
//
//            detail = (InspectionDetailEntity) detail.Save(user);
////  双写 适配问题多选
//            InspectionDetailIssueEntity inspectionDetailIssueEntity = new InspectionDetailIssueEntity();
//
//            inspectionDetailIssueEntity.comments = detail.comments;
//            inspectionDetailIssueEntity.imgs = detail.imgs;
//            inspectionDetailIssueEntity.jobReceiver = detail.jobReceiver;
//            inspectionDetailIssueEntity.jname = detail.jname;
//            inspectionDetailIssueEntity.jobTypeIds = detail.jobTypeIds;
//            inspectionDetailIssueEntity.jobTaskId = detail.jobTaskId;
//
//            inspectionDetailIssueEntity.inspId=insp.id;
//            inspectionDetailIssueEntity.inspDetailId=detail.id;
//            inspectionDetailIssueEntity.id = inspectionDetailIssueEntity.createNewId();
//            inspectionDetailIssueEntity.Save(user);
//
//            if (!detail.isAdaptation)
//                continue;
//
//            totalScore = totalScore + PrimitiveUtils.convertToFloat(detail.score, 0f);
//
//            if (detail.isAssignJob) {
//                if (detail.jobReceiver == null || detail.jobReceiver.longValue() == 0)
//                    throw new RatelRuntimeException("未指定任务接收人");
//
//                issueCount++;
//                String watchids = "";
//				/*if (!PrimitiveUtils.isNullLong(insp.receiverId, true))
//					watchids = insp.receiverId.toString();*/
//                if (StringUtils.isNotBlank(insp.receiverId))
//                    watchids = insp.receiverId;
//                String jobTypeIds = "";// detail.jobTypeIds
//                String jobTypeNames = typeInfo.groupName;
//
//                // 2018.5.3是否要判断？
//                // if (PrimitiveUtils.isNullString(detail.comments))
//                // throw new RatelRuntimeException("生成任务时必须输入描述！");
//                // if (PrimitiveUtils.isNullString(detail.imgs))
//                // throw new RatelRuntimeException("生成任务时必须上传图片！");
//                String questionDecs = detail.comments;
//                if(questionDecs==null||"".equals(questionDecs)) {
//                    questionDecs = detail.inspElementName;
//                }
//                job.add(new assignJob(insp.id, detail.id, insp.shopId, detail.jobReceiver, questionDecs, detail.imgs, jobTypeIds, jobTypeNames, watchids, "1",null,null,null));
//            }
//        }
//        if (watchers != null && !watchers.isEmpty()) {
//            for (InspectionWatcherEntity watcher : watchers) {
//                watcher.id = watcher.createNewId();
//                watcher.inspectionId = insp.id;
//                watcher = (InspectionWatcherEntity) watcher.Save(user);
//            }
//        }
//        insp.score = totalScore;
//        insp.issueCount = issueCount;
//        // code
//        // String code = "";
//        // code += PrimitiveUtils.convertDateToString(new Date(), "yyyyMMdd");
//        // code += "-" + insp.shopCode;
//        // int rand = (int) (Math.random() * 100);
//        // code += "-" + rand;
//        insp.code = insp.buildCode(user);
//        InspectionEntity inspNew = (InspectionEntity) insp.Save(user);
//
//        // 增加模板使用次数
//        addTempReportCount(inspNew.templateId);
//
//        String sql = "update om_si_template_main set report_count=(select sum(report_count) from om_si_template where template_root_id=?) where template_root_id=?";
//        List<Object> paras = new ArrayList<>();
//        paras.add(template.templateRootId);
//        paras.add(template.templateRootId);
//        JPAHelper.nativeExecute(sql, paras);
//
//        // 发送任务接口。
//        assignShopguideJob(accessToken, job);
//        return inspNew;
//    }
//
//
//    /**
//     * 针对问题解答责任人多选
//     * @param accessToken
//     * @param user
//     * @param insp
//     * @return
//     * @throws Exception
//     */
//    public InspectionEntity inspectSaveNew(String accessToken, UserInfo user, InspectionEntity insp) throws Exception {
//        //参数校验
//        inspectSave_CheckNew(insp);
//        InspTemplateSvc svc = InspTemplateCtl.getService();
//        //获取的巡检表,用于类型检查
//        TemplateEntity template = svc.templateDetail(user, insp.templateId);
//
//        // 问题用户ID
////        List<Long> userIdList = new ArrayList<>();
//        //封装检查项数据
//        Map<String, jobTypeInfo> answerScore = buildElementScoreMap(template);
//
//        insp.id = insp.createNewId();
//        insp.inspectorId = user.getSysUserId();
//        insp.inspectorName = user.getName();
//        //设置模板满分值，用于扣分制情形
//        insp.fullMarks = template.fullMarks;
//
//        //一对多处理(巡店报告接收人 多人处理)
//        List<Long> reportReceiverIdList = saveReportReceiver(user, insp);
//
//        int issueCount = 0;
//        //巡检报告总得分,巡检报告满分
//        float inspScore = 0f, inspFullMark = 0f;
//        List<assignJob> job = new ArrayList<>();
//
//        //根据巡检项组名称，转化为组的概念,同时构建巡检项
//        List<List<InspectionDetailEntity>> detailList = new ArrayList<>();
//        List<InspectionDetailEntity> detailsTemp = new ArrayList<>();
//        String groupName = "";
//        for (InspectionDetailEntity detail : insp.details) {
//            detail.id = detail.createNewId();
//            detail.inspectionId = insp.id;
//            jobTypeInfo typeInfo = getChoiceScore(answerScore, detail.inspElementId);
//            if (typeInfo.score != null){
//                detail.templateScore = typeInfo.score;
//            }
//            //扣分制情况下，得分转化为负分
//            if (insp.scoreRule != null && insp.scoreRule == 1) {
//                detail.score = -detail.score;
//            }
//
//            // 增加保存冗余信息
//            detail.inspElementName = typeInfo.eleName;
//            detail.inspElementDesc = typeInfo.eleDesc;
//            detail.groupId = typeInfo.groupId;
//            detail.groupName = typeInfo.groupName;
//            detail.groupShowOrder = typeInfo.groupOrder;
//            detail.elementShowOrder = typeInfo.eleOrder;
//
//            if(!detail.groupName.equals(groupName)){
//                groupName = detail.groupName;
//                detailsTemp = new ArrayList<>();
//                detailList.add(detailsTemp);
//            }
//            detailsTemp.add(detail);
//        }
//
//        for (List<InspectionDetailEntity> list: detailList) {
//            //组得分,组满分
//            float groupScore = 0f,groupFullMarks = 0f;
//            //一票否决标识 (一个分类下可能有多个一票否决项)
//            boolean isOneVoteVetoFlag = false;
//
//            for (InspectionDetailEntity detail: list) {
//                if (!detail.isAdaptation) {
//                    detail.score = null;
//                } else {
//                    //一票否决项判断
//                    if (detail.isOneVoteVeto && Boolean.FALSE.toString().equals(detail.isQualified)) {
//                        isOneVoteVetoFlag = true;
//                    }
//                    if (ItemType.isScoring(detail.itemType) && detail.score != null) {
//                        //组得分,组满分
//                        groupScore += PrimitiveUtils.convertToFloat(detail.score, 0f);
//                        groupFullMarks += PrimitiveUtils.convertToFloat(detail.templateScore, 0f);
//                    }
//                }
//                detail.Save(user);
//                if (!detail.isAdaptation) {
//                    continue;
//                }
//
//                if (detail.isAssignJob && CollectionUtils.isNotEmpty(detail.issue)) {
//                    for (InspectionDetailIssueEntity issue : detail.issue) {
//
//                        // 添加问题次数
////						Long jobReceiver = inspectionDetailIssueEntity.jobReceiver;
////						userIdList.add(jobReceiver);
//
//                        //任务有接收人 才算问题
////						if ((StringUtils.isNotBlank(issue.outSider))||(issue.jobReceiver != null && issue.jobReceiver != 0)){
////							issueCount++;
////
//////							String outSider = inspectionDetailIssueEntity.outSider;
//////							String[] split = outSider.split(",");
//////							Long[] convert = (Long[]) ConvertUtils.convert(split, Long.class);
//////							userIdList.addAll(Arrays.asList(convert));
////
////						}
//                        String watchids = "";
//					/*if (!PrimitiveUtils.isNullLong(insp.receiverId, true))
//						watchids = insp.receiverId.toString();*/
//					/*if (StringUtils.isNotBlank(insp.receiverId))
//						watchids = insp.receiverId;*/
//                        String jobTypeIds = "";// detail.jobTypeIds
//                        String jobTypeNames = detail.groupName;
//                        issue.inspId=insp.id;
//                        issue.inspDetailId=detail.id;
//                        issue.id = issue.createNewId();
//                        issue = (InspectionDetailIssueEntity) issue.Save(user);
//
//                        // 2018.5.3是否要判断？
//                        // if (PrimitiveUtils.isNullString(detail.comments))
//                        // throw new RatelRuntimeException("生成任务时必须输入描述！");
//                        // if (PrimitiveUtils.isNullString(detail.imgs))
//                        // throw new RatelRuntimeException("生成任务时必须上传图片！");
//                        String questionDecs = issue.comments;
//                        if (StringUtils.isEmpty(questionDecs)) {
//                            questionDecs = detail.inspElementName;
//                        }
//                        //判断字符串jobReceiver 是否是null
//                        if (issue.jobReceiver == null) {
//                            issue.jobReceiver = 0L;
//                        }
//                        //任务截至时间，获取优先级  问题 > 报告 > null
//                        Date feedbackDeadline = detail.feedbackDeadline != null ? detail.feedbackDeadline : insp.feedbackDeadline;
//                        //添加保存店外人员字段outSider
//                        if ((StringUtils.isNotBlank(issue.outSider)) || (issue.jobReceiver != null && issue.jobReceiver != 0)) {
//                            issueCount++;
//                            job.add(new assignJob(insp.id, detail.id, insp.shopId, issue.jobReceiver, questionDecs, issue.imgs, jobTypeIds, jobTypeNames, watchids, "1", issue.video, issue.outSider, issue.id, feedbackDeadline));
//                        }
//                    }
//                }
//            }
//
//            //处理一票否决情形，该分类下一票否决项最终的分为0分，则本分类最终得分为0分
//            //加分制,本分类0分
//            if (insp.scoreRule != null && insp.scoreRule == 0 && isOneVoteVetoFlag) {
//                groupScore = 0.0f;
//            }
//            //扣分制, 本分类扣满分, 显示为负分
//            if (insp.scoreRule != null && insp.scoreRule == 1 && isOneVoteVetoFlag) {
//                groupScore = -groupFullMarks;
//            }
//
//            //各组得分总和为巡检报告得分
//            inspScore += groupScore;
//            //各组满分总和为巡检报告满分
//            inspFullMark += groupFullMarks;
//        }
//
//        if (CollectionUtils.isNotEmpty(insp.watchers)) {
//            for (InspectionWatcherEntity watcher : insp.watchers) {
//                watcher.id = watcher.createNewId();
//                watcher.inspectionId = insp.id;
//                watcher.Save(user);
//            }
//        }
//
//        //加分制
//        if (insp.scoreRule != null && insp.scoreRule == 0) {
//            insp.score = inspScore;
//            insp.sourceScore = inspFullMark;
//        }
//        //扣分制
//        if (insp.scoreRule != null && insp.scoreRule == 1) {
//            insp.score = insp.fullMarks + inspScore;
//            //扣分制的情形下，需要改变展示字段 sourceScore 的值为模板表的满分值
//            insp.sourceScore = insp.fullMarks;
//        }
//
//        insp.issueCount = issueCount;
//        insp.code = insp.buildCode(user);
//
//        //计算的得分率
//        float rate = 0.0f;
//        DecimalFormat df = new DecimalFormat("0.0");
//        if (insp.sourceScore != null && insp.sourceScore != 0) {
//            rate = insp.score / insp.sourceScore;
//        }
//        insp.scoreRate = df.format(rate * 100) + "%";
//
//        InspectionEntity inspNew = (InspectionEntity) insp.Save(user);
//
//        // 增加模板使用次数
//        addTempReportCount(inspNew.templateId);
//
//        String sql = "update om_si_template_main set report_count=(select sum(report_count) from om_si_template where template_root_id=?) where template_root_id=?";
//        List<Object> paras = new ArrayList<>();
//        paras.add(template.templateRootId);
//        paras.add(template.templateRootId);
//        JPAHelper.nativeExecute(sql, paras);
//
//        // 发送任务接口。
//        assignShopguideJob(accessToken, job);
//
//        // 报告接收人
//        // sendMessage(user, inspNew.id, 1 ,reportReceiverIdList);
//        // 发送消息 问题
//        // sendMessage(user, inspNew.id, 2 ,userIdList);
//        return inspNew;
//    }
//
//    /**
//     * 保存报告接收人
//     * @param user
//     * @param insp
//     * @throws SQLException
//     */
//    private List<Long> saveReportReceiver(UserInfo user, InspectionEntity insp) throws SQLException {
//        List<Long> reportReceiverIdList = new ArrayList<>();
//        if(StringUtils.isNotBlank(insp.receiverId)){
//            List<String> receiverIdList=Arrays.asList(insp.receiverId.split(","));
//            List<String> receiverNameList=Arrays.asList(insp.receiverName.split(","));
//
//            String sql  = " INSERT INTO om_si_report_receiver ( id, insp_id,inviter, receiver_id, receiver_name, create_time, create_user ,";
//            sql += " update_time, update_user, delete_flag, app_id ) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
//            List<List<Object>> sqlPara = new ArrayList<>();
//            if(CollectionUtils.isNotEmpty(receiverIdList)){
//                for (int i = 0; i < receiverIdList.size(); i++) {
//                    String id = insp.createNewId();
//                    String inspId = insp.id;
//                    String inviter= insp.inspectorId.toString();
//                    String receiverId=receiverIdList.get(i);
//                    reportReceiverIdList.add(NumberUtils.obj2Long(receiverId));
//                    String receiverName=receiverNameList.get(i);
//                    Date createTime=new Date();
//                    Long createUser= user.getSysUserId();
//                    Date updateTime=new Date();
//                    Long updateUser= user.getSysUserId();
//                    int deleteFlag=0;
//                    String appId= user.getAppId();
//
//                    List<Object> paras = new ArrayList<>();
//                    paras.add(id);
//                    paras.add(inspId);
//                    paras.add(inviter);
//                    paras.add(receiverId);
//                    paras.add(receiverName);
//                    paras.add(createTime);
//                    paras.add(createUser);
//                    paras.add(updateTime);
//                    paras.add(updateUser);
//                    paras.add(deleteFlag);
//                    paras.add(appId);
//                    sqlPara.add(paras);
//                }
//                JPAHelper.nativeExecutePrepareBatch(sql, sqlPara);
//            }
//        }
//        return reportReceiverIdList;
//    }
//
//
//    /**
//     * 上报总分矫正
//     * @param accessToken
//     * @param user
//     * @return
//     * @throws Exception
//     */
//    public InspectionEntity scoreCorrection(String accessToken, UserInfo user, String id) throws Exception {
//        //查询所有上报项
//        String sql = String.format(" SELECT " +
//                " m.*, " +
//                " t.NAME template_name  " +
//                " FROM " +
//                " om_si_inspection m " +
//                " JOIN om_si_template t ON t.id = m.template_id  " +
//                " WHERE " +
//                " m.create_time >= ? ");
//        List<Object> paras = new ArrayList<>();
//        paras.add("2021-1-28 12:00:00");
//
//        if(StringUtils.isNotBlank(id)){
//            sql += " and m.id = ? ";
//            paras.add(id);
//        }
//
//        List<InspectionEntity> list = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class);
//
//        //遍历所有上报详情，重新计算
//        for (int i = 0; i < list.size(); i++) {
//
//            InspectionEntity inspectionEntity = list.get(i);
//
//            Float oldScore = inspectionEntity.score;
//
//            InspectionEntity insp = recalculateScore(inspectionEntity);
//
//            //根据返回对象做对比，如果当前分数与计算后分数不一致，则修改为重新计算后的分数
//            if (!oldScore.equals(insp.score)){
//                //更新分数
//                String sqlTemp = " update om_si_inspection set score = ?  where id = ?";
//
//                List<Object> paras3 = new ArrayList<>();
//                paras3.add(insp.score);
//                paras3.add(insp.id);
//                int result = JPAHelper.nativeExecute(sqlTemp, paras3);
//                if (result > 0){
//                    System.out.println("执行成功");
//                }
//            }
//
//        }
//        return null;
//    }
//
//    /**
//     * 重新计算分数
//     * @param inspectionEntity
//     * @return
//     * @throws InstantiationException
//     * @throws IllegalAccessException
//     */
//    private InspectionEntity recalculateScore(InspectionEntity inspectionEntity) throws InstantiationException, IllegalAccessException {
//        //查询该巡店报告下面所有巡检明细
//        String sql2 = "select *,0 total_element_count,0 total_score from om_si_inspection_detail where inspection_id=? order by group_show_order,element_show_order";
//        List<Object> paras2 = new ArrayList<Object>();
//        paras2.add(inspectionEntity.id);
//        @SuppressWarnings("rawtypes")
//        List<HashMap> maps = JPAHelper.nativeQuery(sql2, paras2, HashMap.class);
//        if (maps == null || maps.isEmpty()) {
//            return null;
//        }
//
//        List<InspectionDetailEntity> details = ModelHelper.convertMapToModel(maps, InspectionDetailEntity.class);
//        HashMap<String, InspectionDetailEntity> detailsMap = new HashMap<>();
//        for (InspectionDetailEntity d : details) {
//            detailsMap.put(d.id, d);
//        }
//
//        //根据巡检项组名称，转化为组的概念
//        List<List<InspectionDetailEntity>> detailList = new ArrayList<>();
//        List<InspectionDetailEntity> detailsTemp = new ArrayList<>();
//        String name = "";
//        for (InspectionDetailEntity d : details) {
//            if(!d.groupName.equals(name)){
//                name = d.groupName;
//                detailsTemp = new ArrayList<>();
//                detailList.add(detailsTemp);
//            }
//            detailsTemp.add(d);
//        }
//
//
//        inspectionEntity.groupDetails = new ArrayList<InspectionGroupDetailEntity>();
//
////			//查询该巡店报告下面所有问题
////			String issueSql = "select * from om_si_insp_detail_issue where insp_id = ?";
////			List<Object> param = new ArrayList<Object>();
////			param.add(inspectionEntity.id);
////			@SuppressWarnings("rawtypes")
////			List<HashMap> issuemaps = JPAHelper.nativeQuery(issueSql, param, HashMap.class);
////			List<InspectionDetailIssueEntity> issues = ModelHelper.convertMapToModel(issuemaps, InspectionDetailIssueEntity.class);
//
//        //重算下分。
//        inspectionEntity.sourceScore = 0f;
//        //检查表得分
//        inspectionEntity.score = 0f;
//        for (List<InspectionDetailEntity> list2: detailList) {
//            //构建检查项组层次，此时为组级别
//            InspectionGroupDetailEntity group = new InspectionGroupDetailEntity();
//            group.name = list2.get(0).groupName;
//            group.detailCount = 0;
//            group.sourceScore = 0;
//            group.score = 0f;
//
//            //一票否决标识 (一个分类下可能有多个一票否决项)
//            boolean isOneVoteVetoFlag = false;
//            for (InspectionDetailEntity ide: list2) {
//                //此时为组的下一层，具体检查项级别
//
//                InspectionDetailEntity d = detailsMap.get(ide.id);
////					//处理检查项下的问题列表
////					if(issues!=null) {
////						for (InspectionDetailIssueEntity issue : issues) {
////							//视频关键字转化操作
////							if(StringUtils.isNotBlank(issue.video) && StringOperatorUtils.stringContains(issue.video) ){
////								issue.video=StringOperatorUtils.stringReplace(issue.video);
////							}
////							//为检查项添加问题列表
////							if(issue.inspDetailId.equals(ide.id)) {
////								d.issue.add(issue);
////							}
////							// todo  这个循环可以做优化，已经添加过的数据可以做剔除
////						}
////					}
//
//                //处理检查项的得分
//                if (!d.isAdaptation) {
//                    //不适用
//                    d.score = null;
//                } else if (d.itemType == 0) {
//                    //只有评分项才有计算的意义
//                    //组得分
//                    if (d.score != null) {
//
//                        //扣分制情况下，得分转化为负分
//                        if (inspectionEntity.scoreRule != null && inspectionEntity.scoreRule == 1) {
//                            if (d.score > 0){
//                                d.score = -d.score;
//                            }
//                        }
//
//                        group.score += d.score;
//
//                        //一票否决判断
//                        if (d.isOneVoteVeto){
//                            //加分制，扣分制 分开处理
//                            //加分制,得分为0
//                            if(inspectionEntity.scoreRule!= null && inspectionEntity.scoreRule == 0 && d.score == 0){
//                                isOneVoteVetoFlag = true;
//                            }
//                            //扣分制, 扣分为此项的满分
//                            if(inspectionEntity.scoreRule!= null && inspectionEntity.scoreRule == 1 && -d.score == d.templateScore){
//                                isOneVoteVetoFlag = true;
//                            }
//                        }
//                    }
//                    //组满分
//                    group.sourceScore += d.templateScore;
//                }
//                group.detailCount++;
//                group.details.add(d);
//            }
//
//            //处理一票否决情形，该分类下一票否决项最终的分为0分，则本分类最终得分为0分
//            //加分制,本分类0分
//            if(inspectionEntity.scoreRule!= null && inspectionEntity.scoreRule == 0 && isOneVoteVetoFlag){
//                group.score = 0.0f;
//            }
//            //扣分制, 本分类扣满分
//            if(inspectionEntity.scoreRule!= null && inspectionEntity.scoreRule == 1 && isOneVoteVetoFlag){
//                group.score = -group.sourceScore;
//            }
//
//            //检查表得分
//            inspectionEntity.score += group.score;
//
//            //检查表满分
//            inspectionEntity.sourceScore += group.sourceScore;
//            inspectionEntity.groupDetails.add(group);
//        }
//
//        //处理上报显示的得分、显示的总分
//        //扣分制
//        if (inspectionEntity.scoreRule != null && inspectionEntity.scoreRule == 1) {
//            inspectionEntity.sourceScore = inspectionEntity.fullMarks;
//            inspectionEntity.score = inspectionEntity.sourceScore + inspectionEntity.score;
//        }
//
//        return inspectionEntity;
//    }
//
//
//    private void addTempReportCount(String templateId) {
//        // 正式环境并发修改有锁表的情况，致使修改时间变得很长。
//        // 暂时这样处理， 后续需要用的分布式锁，用查询后再修改的方式
//        // synchronized (templateId) {
//        // 记录模板的使用次数。
//        String sql = "update om_si_template set report_count=ifnull(report_count,0)+1 where id=?";
//        List<Object> paras = new ArrayList<>();
//        paras.add(templateId);
//        JPAHelper.nativeExecute(sql, paras);
//        // }
//    }
//
//    /**
//     * 发送消息
//     * @param user
//     * @param id
//     * @param type
//     * @param userId
//     */
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    private void sendMessage(UserInfo user, String id, int type, List<Long> userId) {
//        RemindDto remindDto = new RemindDto();
//
//        List<RangeDto> rangeDtos = new ArrayList<>();
//        RangeDto rangeDto = new RangeDto();
//        rangeDto.setRangeType(Common.RangeType.user);
//        rangeDto.setIds(userId);
//        rangeDtos.add(rangeDto);
//        remindDto.setRange(rangeDtos);
//
//        String title = "";
//        String desc = "";
//
//        Common.RemindType remindType;
//        if(type == 1){
//            title = "你有一份巡店报告";
//            desc = "请点击查看报告详情!";
//            remindType = Common.RemindType.report_detail;
//        }else{
//            title = "你有新的巡店问题待反馈";
//            desc = "请及时调整后进行反馈!";
//            remindType = Common.RemindType.wait_feedback;
//        }
//
//        remindDto.setRemindType(remindType);
//        remindDto.setRemindTitle(title);
//        remindDto.setRemindDesc(desc);
//
//        remindDto.setBusinessType(Common.BusinessType.storeinsp);
//        remindDto.setBusinessId(id);
//
//        // 路由参数
//        Map routeparam = new HashMap();
//        routeparam.put("id", id);
//        remindDto.setBusinessParam(JSonHelper.toJSon(routeparam));
//
//        remindDto.setAppId(user.getAppId());
//        remindDto.setCreateUser(user.getSysUserId());
//
//        try {
//            RemindUtils.requestMessageRemindPost(remindDto);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     *
//     * 根据类型获取标题
//     * @param type
//     * @return
//     */
//    private String getTitleByType(int type) {
//        if(type == 1){
//            return "新的报告";
//        }else if (type == 2){
//            return "您有新的问题待处理";
//        }
//        return null;
//    }
//
//    /**
//     * 添加次数
//     * @param userCountMap
//     * @param receiver
//     */
//    private void putUserCount(Map<Long, Integer> userCountMap, Long receiver) {
//        Integer count = userCountMap.get(receiver);
//        if(count == null){
//            userCountMap.put(receiver, 1);
//        }else{
//            userCountMap.put(receiver, count + 1);
//        }
//    }
//
//
//
//    // private Integer getChoiceScore(Map<String, Integer> answerScore, String choice) {
//    // Integer score = answerScore.get(choice.toLowerCase());
//    // return score;
//    // }
//
//    // private Map<String, Integer> buildChoiceScoreMap(TemplateEntity template) {
//    // Map<String, Integer> map = new Hashtable<>();
//    // for (TemplateElementGroupEntity group : template.groups) {
//    // for (TemplateElementEntity element : group.elements) {
//    // for (TemplateElementChoiceEntity choice : element.choices) {
//    // map.put(choice.id.toLowerCase(), choice.score);
//    // }
//    // }
//    // }
//    //
//    // return map;
//    // }
//
//    /**
//     *	cxy：说是叫做上报删除前检查，其实仅检测了改上报详情是否生成了任务，是否有任务接收人，而且仅在移动端有效
//     * @param accessToken
//     * @param user
//     * @param insp
//     * @param fromType
//     *            1:移动端 2管理端。
//     * @return
//     * @throws Exception
//     */
//    public InspectionEntity inspectionDeleteCheck(String accessToken, UserInfo user, InspectionEntity insp, Integer fromType) throws Exception {
//        if (fromType == null) {
//            throw new RatelRuntimeException("请传入类型！");
//        }
//        if (insp == null || PrimitiveUtils.isNullString(insp.id)){
//            throw new RatelRuntimeException("请传入巡检报告！");
//        }
//
//        String sql = String.format("select m.* from om_si_inspection m where m.app_id=? and m.id=?");
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        paras.add(insp.id);
//        List<InspectionEntity> list = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class);
//        if (list == null || list.size() != 1) {
//            throw new RatelRuntimeException("未找到巡检报告！");
//        }
//        InspectionEntity inspNew = list.get(0);
//        if (inspNew.deleteFlag) {
//            throw new RatelRuntimeException("巡店报告已被删除！");
//        }
//
//        inspNew.buildDetails();
//
//        HashMap<String, InspectionDetailEntity> jobs = new HashMap<>();
//        for (InspectionDetailEntity row : inspNew.details) {
//            if (!row.isAdaptation) {
//                continue;
//            }
//
//            if (row.isAssignJob) {
//                jobs.put(row.jobTaskId, row);
//            }
//        }
//
//        if (jobs.size() > 0) {
//            if (fromType.intValue() == 1) {
//                throw new RatelRuntimeException("100002", "此报告有关联问题，不能被删除。");
//            } else if (fromType.intValue() == 2) {
//                // 删除问题。
//                // deleteShopguideJob(accessToken, jobs);
//            }
//        }
//
//        return inspNew;
//    }
//
//    /**
//     *
//     * @param accessToken
//     * @param user
//     * @param insp
//     * @param fromType
//     *            1:移动端 2管理端。
//     * @return
//     * @throws Exception
//     */
//    public Integer inspectionDelete(String accessToken, UserInfo user, InspectionEntity insp, Integer fromType) throws Exception {
//        InspectionEntity inspNew = inspectionDeleteCheck(accessToken, user, insp, fromType);
//
//        HashMap<String, InspectionDetailEntity> jobs = new HashMap<>();
//        for (InspectionDetailEntity row : inspNew.details) {
//            if (!row.isAdaptation) {
//                continue;
//            }
//
//            if (row.isAssignJob) {
//                jobs.put(row.jobTaskId, row);
//            }
//        }
//
//        //此处与上报检查相冲突，如果存在上报详情的问题任务接收人，那么就会抛出异常，而不会是走到这里
//        if (jobs.size() > 0) {
////			if (fromType.intValue() == 1) {
//////				throw new RatelRuntimeException("此报告有关联问题，不能被删除。");
////				//modified  张永涛需求三十二期需求 移动端也可以删除  2018/8/15
////				// 删除问题。
////				deleteShopguideJob(accessToken, jobs);
////
////			} else if (fromType.intValue() == 2) {
////				// 删除问题。
////				deleteShopguideJob(accessToken, jobs);
////			}
//
//            //简化代码
//            if (fromType == 1 || fromType == 2) {
//                // 删除问题。
//                deleteShopguideJob(accessToken, jobs);
//            }
//        }
//
//        //关于下面步骤有一个疑问：删除上报记录的时候 ，为什么不把该上报记录下的详情记录也删除呢？
//        String sql = "update om_si_inspection set delete_flag=1,delete_user=?,delete_time=? where app_id=? and id=?";
//        List<Object> paraValues = new ArrayList<>();
//        paraValues.add(user.getSysUserId());
//        paraValues.add(new Date());
//        paraValues.add(user.getAppId());
//        paraValues.add(insp.id);
//
//        return JPAEntityManager.nativeExecute(sql, paraValues);
//    }
//
//    private jobTypeInfo getChoiceScore(Map<String, jobTypeInfo> answerScore, String elementId) {
//        jobTypeInfo typeInfo = answerScore.get(elementId.toLowerCase());
//        return typeInfo;
//    }
//
//    /**
//     * 把巡检表对象转化为  巡检项id为key, 项总分，分类名称，项名称，项描述，分类id，
//     * @param template
//     * @return
//     */
//    private Map<String, jobTypeInfo> buildElementScoreMap(TemplateEntity template) {
//        Map<String, jobTypeInfo> map = new Hashtable<>();
//        for (TemplateElementGroupEntity group : template.groups) {
//            for (TemplateElementEntity element : group.elements) {
//                // map.put(element.id.toLowerCase(), new jobTypeInfo(element.totalScore, group.name));
//                map.put(element.id.toLowerCase(), new jobTypeInfo(element.totalScore, group.name, element.name, element.description, group.id, group.showOrder, element.showOrder));
//            }
//        }
//
//        return map;
//    }
//
//    /**
//     * 根据巡检表模板检查上报项内容的正确性
//     * @param user
//     * @param insp
//     * @param template
//     * @throws Exception
//     */
//    private void inspectSave_Check(UserInfo user, InspectionEntity insp, TemplateEntity template
//                                   // , Map<String, Integer> answerScore
//    ) throws Exception {
//        if (PrimitiveUtils.isNullLong(insp.shopId, true)) {
//            throw new RatelRuntimeException("未指定店铺！");
//        }
//        if (PrimitiveUtils.isNullString(insp.shopName)) {
//            throw new RatelRuntimeException("未指定店铺名称！");
//        }
//        // if (PrimitiveUtils.isNullString(insp.shopAddress))
//        // throw new RatelRuntimeException("未指定店铺地址！");
//
//        if (PrimitiveUtils.isNullString(insp.templateId)) {
//            throw new RatelRuntimeException("未指定巡检表！");
//        }
//
//        List<InspectionDetailEntity> details = insp.details;
//        List<InspectionWatcherEntity> watchers = insp.watchers;
//        if (details == null || details.isEmpty()) {
//            throw new RatelRuntimeException("请传入巡检明细！");
//        }
//
//        List<Long> job_reid = new ArrayList<>();
//        for (InspectionDetailEntity detail : details) {
//            if (PrimitiveUtils.isNullString(detail.inspElementId)) {
//                throw new RatelRuntimeException("未指定巡检项！");
//            }
//
//            if (PrimitiveUtils.isNullFloat(detail.score, false)) {
//                detail.score = 0f;
//            }
//            // throw new RatelRuntimeException("巡检项未打分！");
//
//            // if (PrimitiveUtils.isNullString(detail.choiceId))
//            // throw new RatelRuntimeException("巡检项未选择！");
//            // Integer score = getChoiceScore(answerScore, detail.choiceId);
//            // if (PrimitiveUtils.isNullInteger(score, false))
//            // throw new RatelRuntimeException("未在模板中找到选择项！");
//
//            if (detail.isAssignJob) {
//                if (detail.jobReceiver == null || detail.jobReceiver.longValue() == 0) {
//                    throw new RatelRuntimeException("未指定任务接收人");
//                }
//
//                job_reid.add(detail.jobReceiver);
//            }
//        }
//
//        //
//        if (watchers != null && !watchers.isEmpty()) {
//            for (InspectionWatcherEntity watcher : watchers) {
//                if (PrimitiveUtils.isNullLong(watcher.watcherId, true)) {
//                    throw new RatelRuntimeException("未指定关注者！");
//                }
//            }
//        }
//    }
//
//    private void inspectSave_CheckNew(InspectionEntity insp) {
//        if (PrimitiveUtils.isNullLong(insp.shopId, true)) {
//            throw new RatelRuntimeException("未指定店铺！");
//        }
//        if (PrimitiveUtils.isNullString(insp.shopName)) {
//            throw new RatelRuntimeException("未指定店铺名称！");
//        }
//        if (PrimitiveUtils.isNullString(insp.templateId)) {
//            throw new RatelRuntimeException("未指定巡检表！");
//        }
//        if (CollectionUtils.isEmpty(insp.details)) {
//            throw new RatelRuntimeException("请传入巡检明细！");
//        }
//        for (InspectionDetailEntity detail : insp.details) {
//            if (PrimitiveUtils.isNullString(detail.inspElementId)) {
//                throw new RatelRuntimeException("未指定巡检项！");
//            }
//            if (PrimitiveUtils.isNullFloat(detail.score, false)) {
//                detail.score = 0f;
//            }
//            detail.issue.forEach(x -> {
//                if (StringUtils.isEmpty(x.comments)){
//                    throw new RatelRuntimeException("请输入问题描述");
//                }
//            });
//
//        }
//        //todo 测试时间：2021年3月26日15:50:46，如果无报错，则一段时间后删除watchers相关操作
//        if (CollectionUtils.isNotEmpty(insp.watchers)) {
//            for (InspectionWatcherEntity watcher : insp.watchers) {
//                if (PrimitiveUtils.isNullLong(watcher.watcherId, true)) {
//                    throw new RatelRuntimeException("未指定关注者！");
//                }
//            }
//        }
//    }
//
//    /**
//     *
//     * @param user
//     * @param pageIndex
//     * @param pageSize
//     * @param keyword
//     * @param inspectorId
//     * @param shopId
//     * @param dateBegin
//     * @param dateEnd
//     * @param loginType
//     *            : 1时为管理端登录。
//     * @param timeType
//     * @param reportCode
//     * @return
//     * @throws Exception
//     */
//    public PageResult<?> inspectionQuery(String accessToken, UserInfo user, Integer pageIndex, Integer pageSize, String keyword, Long inspectorId, Long shopId, String dateBegin,
//                                         String dateEnd, Integer loginType, String inspector, String shop, Long areaId, Long agentId,Long departId,Long companyId, String reportCode, String timeType,String interfaceType) throws Exception {
//        // UserInfo user = Cache.getValue(token);
//        String sql = String.format(
////				"select m.*,t.total_score sourceScore from om_si_inspection m left join om_si_template t on m.template_id=t.id where m.delete_flag=0 and m.app_id=? ");
//                "select DISTINCT m.*,t.total_score sourceScore,t.`name` template_name from om_si_inspection m left join om_si_template t on m.template_id=t.id  LEFT JOIN om_si_report_receiver rr on m.id=rr.insp_id where m.delete_flag=0 and m.app_id=? ");
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        // 2018.5.3，特步要求在管理端查看巡店报告时，查看有店铺权限的报告。
//        if (loginType != null && loginType.equals(1)) {
//            String condition = "1=1";
//            if (!PrimitiveUtils.isNullLong(areaId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(areaId, "") + "_4,%'";
//            }
//            if (!PrimitiveUtils.isNullLong(departId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(departId, "") + "_3,%'";
//            }
//            if (!PrimitiveUtils.isNullLong(agentId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(agentId, "") + "_6,%'";
//            }
//            if (!PrimitiveUtils.isNullLong(companyId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(companyId, "") + "_2,%'";
//            }
//            sql += " and " + getAuthShopString(accessToken, "m.shop_id", condition);
//        } else {
//            // if (loginType == null || !loginType.equals(1)) {
//            // 默认情况下只有巡检人或报告接收人可以看。
//            sql += " and (m.inspector_id=? or rr.receiver_id=? ";
//            paras.add(user.getSysUserId());
//            paras.add(user.getSysUserId());
//
//
//            //店铺Id
//            if (!PrimitiveUtils.isNullLong(shopId, true)) {
//                sql += " or m.shop_id=?";
//                paras.add(shopId);
//            }
//
//            sql += ")";
//        }
//
//        //关键字段搜索
//        if (!PrimitiveUtils.isNullString(keyword)) {
//            sql += " and (m.name like ? or m.code like ? or t.`name` like ?)";
//            paras.add("%" + keyword + "%");
//            paras.add("%" + keyword + "%");
//            paras.add("%" + keyword + "%");
//        }
//
//        //巡检人编码
//        if (!PrimitiveUtils.isNullLong(inspectorId, true)) {
//            sql += " and m.inspector_id=?";
//            paras.add(inspectorId);
//        }
//
//        //巡检人姓名
//        if (!PrimitiveUtils.isNullString(inspector)) {
//            sql += " and m.inspector_name like ?";
//            paras.add("%" + inspector + "%");
//        }
//        //店铺名称
//        if (!PrimitiveUtils.isNullString(shop)) {
//            sql += " and (m.shop_name like ? or m.shop_code like ?)";
//            paras.add("%" + shop + "%");
//            paras.add("%" + shop + "%");
//        }
//
//        //报告编码
//        if (!PrimitiveUtils.isNullString(reportCode)) {
//            sql += " and m.code like ? ";
//            paras.add("%" + reportCode + "%");
//        }
//
//        //时间类型筛选判断
//        if(StringUtils.isNotBlank(timeType)){
//            if(timeType.equals("1")){//今天
//                sql += " and to_days(m.create_time) = to_days(now()) ";
//            }
//            if(timeType.equals("2")){//近一周
//                sql += " and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(m.create_time) ";
//            }
//            if(timeType.equals("3")){//近一个月
//                sql += " and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(m.create_time) ";
//            }
//            if(timeType.equals("4")){//近三个月
//                sql += " and DATE_SUB(CURDATE(), INTERVAL 90 DAY) <= date(m.create_time) ";
//            }
//        }
//
//        //开始时间
//        if (!PrimitiveUtils.isNullString(dateBegin)) {
//            Date begin = PrimitiveUtils.convertToDate(dateBegin.replaceAll("'", ""), null);
//            if (begin != null) {
//                sql += " and m.create_time>=?";
//                paras.add(PrimitiveUtils.convertDateToString(begin, "yyyy-MM-dd") + " 0:0:0");
//            }
//        }
//        //结束时间
//        if (!PrimitiveUtils.isNullString(dateEnd)) {
//            Date end = PrimitiveUtils.convertToDate(dateEnd.replaceAll("'", ""), null);
//            if (end != null) {
//                sql += " and m.create_time<=?";
//                paras.add(PrimitiveUtils.convertDateToString(end, "yyyy-MM-dd") + " 23:59:59");
//            }
//        }
//
//        // 如果有shopid，就强制只取店铺id的。
//        if(StringUtils.isNotBlank(interfaceType) && "1".equals(interfaceType)){//区分业务反馈列表和单独的巡店报告列表
//            if (!PrimitiveUtils.isNullLong(shopId, true)) {
//                sql += " and m.shop_id=?";
//                paras.add(shopId);
//            }
//        }
//
//        sql += " order by m.update_time desc";
//
//        PageResult<?> result = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class, pageIndex, pageSize);
//        // todo 数据库保存总分与得分率之后，可以删除下方逻辑
//        if (result != null && result.data != null) {
//            String ids = "";
//            List<InspectionEntity> rows = (List<InspectionEntity>) result.data;
//            Map<String, InspectionEntity> mapRows = new HashMap<>();
//            for (InspectionEntity row : rows) {
//                ids += "'" + row.id + "',";
//                mapRows.put(row.id, row);
//                if(row.inspectorId!=null && row.inspectorId.longValue()==user.getSysUserId().longValue()) {
//                    //当前登录用户为此报告的巡检人
//                    row.delable="1";
//                }else {
//                    row.delable="0";
//                }
//            }
//            ids = ids.substring(0, ids.length() - 1);
//
//            //此查询仅耗时：30ms
//            //获取每一个巡检表的总分
//            sql = "select inspection_id,sum(case is_adaptation when 1 then template_score else 0 end) template_score from om_si_inspection_detail where app_id='" + user
//                    .getAppId() + "' and inspection_id in (" + ids + ") group by inspection_id";
//            List<HashMap> maps = JPAHelper.nativeQuery(sql, null, HashMap.class);
//
//            if (maps != null) {
//                for (HashMap row : maps) {
//                    String id = PrimitiveUtils.convertToString(row.get("inspection_id"), "");
//                    InspectionEntity insp = mapRows.get(id);
//                    if (insp == null) {
//                        continue;
//                    }
//
//                    //扣分制显示处理
//                    if(insp.scoreRule!=null && insp.scoreRule==1){
//                        insp.sourceScore=insp.fullMarks;
//                    }else{
//                        insp.sourceScore = PrimitiveUtils.convertToFloat(row.get("template_score"), insp.sourceScore);
//                    }
//
//                    //之所以重新计算，是因为数据库没有保存得分率
//                    //计算的得分率
//                    float rate = 0.0f;
//                    DecimalFormat df = new DecimalFormat("0.0");
//
//                    //这里做错误数据兼容，老数据中存在 insp.sourceScore = 0 的情况
//                    if (insp.sourceScore != null && insp.sourceScore != 0) {
//                        rate = insp.score / insp.sourceScore;
//                    }
//
//                    insp.scoreRate = df.format(rate * 100) + "%";
//                }
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 查询巡检人列表
//     * @param accessToken
//     * @param user
//     * @param pageIndex
//     * @param pageSize
//     * @param inspectorName
//     * @return
//     */
//    public List<InspectionEntity> inspectiondeInspectorIdQuery(String accessToken, UserInfo user, Integer pageIndex,
//                                                               Integer pageSize, String inspectorName) {
//        String sql = String.format("select *from om_si_inspection  where app_id=? ");
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//
//        if (!PrimitiveUtils.isNullString(inspectorName)) {
//            sql += " and inspector_name like ? ";
//            paras.add("%" + inspectorName + "%");
//        }
//        //根据上报人 分组的意义是什么
////		sql+=" GROUP BY inspector_id ";
//
////		long l = System.currentTimeMillis();
////		List<InspectionEntity> result =  JPAHelper.nativeQuery(sql, paras, InspectionEntity.class);
////		System.err.println("原耗时：" +(System.currentTimeMillis() - l));
//
//        PageResult result  = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class, pageIndex, pageSize);
//        List<InspectionEntity> list  = new ArrayList<>();
//        if (result.data != null){
//            list = (List<InspectionEntity>) result.data;
//        }
//        return list;
//    }
//
//    public void inspectionExport(String accessToken, UserInfo user, String keyword, Long inspectorId, Long shopId, String dateBegin,
//                                 String dateEnd, Integer loginType, String inspector,String inspectionId, String shop, Long areaId, Long agentId,Long companyId, Long departId, HttpServletResponse response) throws Exception {
//        // UserInfo user = Cache.getValue(token);
//        String sql = String.format(
//                "select m.*,t.total_score sourceScore from om_si_inspection m left join om_si_template t on m.template_id=t.id where m.delete_flag=0 and m.app_id=? ");
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        // 2018.5.3，特步要求在管理端查看巡店报告时，查看有店铺权限的报告。
//        if (loginType != null && loginType.equals(1)) {
//            String condition = " 1=1 ";
//            if (!PrimitiveUtils.isNullLong(areaId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(areaId, "") + "_4,%'";
//            }
//            if (!PrimitiveUtils.isNullLong(departId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(departId, "") + "_3,%'";
//            }
//            if (!PrimitiveUtils.isNullLong(agentId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(agentId, "") + "_6,%'";
//            }
//            if (!PrimitiveUtils.isNullLong(companyId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(companyId, "") + "_2,%'";
//            }
//            sql += " and " + getAuthShopString(accessToken, "m.shop_id", condition);
//        } else {
//            // if (loginType == null || !loginType.equals(1)) {
//            // 默认情况下只有巡检人或报告接收人可以看。
////			sql += " and (m.inspector_id=? or receiver_id=?";
////			paras.add(user.getSysUserId());
////			paras.add(user.getSysUserId());
//
//            // 2017.10.18 增加店铺内所有人都可以看巡店报告。
//            // 2017.12.8 去掉店铺条件。
//            // if (user.getBelongOrg() != null) {
//            // sql += " or m.shop_id=?";
//            // // 如果传了店铺ID就取传入的，否则取当前用户的店铺ID。
//            // if (!PrimitiveUtils.isNullLong(shopId, true))
//            // paras.add(shopId);
//            // else
//            // paras.add(user.getBelongOrg());
//            // }
//
//            if (!PrimitiveUtils.isNullLong(shopId, true)) {
//                sql += " or m.shop_id=?";
//                paras.add(shopId);
//            }
//
////			sql += ")";
//        }
//
//        if (!PrimitiveUtils.isNullString(keyword)) {
//            sql += " and (m.name like ? or m.code like ? or t.`name` like ?)";
//            paras.add("%" + keyword + "%");
//            paras.add("%" + keyword + "%");
//            paras.add("%" + keyword + "%");
//        }
//
//        if (!PrimitiveUtils.isNullLong(inspectorId, true)) {
//            sql += " and m.inspector_id=?";
//            paras.add(inspectorId);
//        }
//
//        if (!PrimitiveUtils.isNullString(inspector)) {
//            sql += " and m.inspector_name like ?";
//            paras.add("%" + inspector + "%");
//        }
//
//        if (!PrimitiveUtils.isNullString(inspectionId)) {
//            sql += " and m.id = ?";
//            paras.add( inspectionId );
//        }
//
//        if (!PrimitiveUtils.isNullString(shop)) {
//            sql += " and (m.shop_name like ? or m.shop_code like ?)";
//            paras.add("%" + shop + "%");
//            paras.add("%" + shop + "%");
//        }
//
//        if (!PrimitiveUtils.isNullString(dateBegin)) {
//            Date begin = PrimitiveUtils.convertToDate(dateBegin.replaceAll("'", ""), null);
//            if (begin != null) {
//                sql += " and m.create_time>=?";
//                paras.add(PrimitiveUtils.convertDateToString(begin, "yyyy-MM-dd") + " 0:0:0");
//            }
//        }
//
//        if (!PrimitiveUtils.isNullString(dateEnd)) {
//            Date end = PrimitiveUtils.convertToDate(dateEnd.replaceAll("'", ""), null);
//            if (end != null) {
//                sql += " and m.create_time<=?";
//                paras.add(PrimitiveUtils.convertDateToString(end, "yyyy-MM-dd") + " 23:59:59");
//            }
//        }
//
//        // 如果有shopid，就强制只取店铺id的。
//        if (!PrimitiveUtils.isNullLong(shopId, true)) {
//            sql += " and m.shop_id=?";
//            paras.add(shopId);
//        }
//
//        sql += " order by m.update_time desc";
//
//        //所需要的所有id
//        List<InspectionEntity> result = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class);
//
//        String sheetName = "业绩上报";
//        //创建导出条件
//        ExportExcelCondition<InspectionExportDto> condition = new ExportExcelCondition<>();
//        //导出操作类型，根据javaBean格式导出
//        condition.setType(ExportExcelCondition.BEAN);
//        String[] headers = {"报告名称", "检查表", "巡店总结", "店铺编码", "店铺名称", "发起人", "接收人", "创建时间", "得分", "满分", "得分率", "分类", "检查项", "检查项标准描述",
//                "问题描述", "问题接收人", "是否适用","满分（检查项）","得分（检查项）","图片1","图片2","图片3","图片4","图片5","图片6","图片7","图片8","图片9","视频","一票否决"};
//        condition.setHeaders(headers);
//
//
//        if (CollectionUtils.isNotEmpty(result)) {
//            StringBuilder ids = new StringBuilder();
////			@SuppressWarnings("unchecked")
////			List<InspectionEntity> rows = (List<InspectionEntity>) result;
////			Map<String, InspectionEntity> mapRows = new HashMap<>();
////			for (InspectionEntity row : rows) {
////				ids += "'" + row.id + "',";
////				mapRows.put(row.id, row);
////			}
//            for (InspectionEntity inspection : result) {
//                ids.append("'").append(inspection.id).append("',");
//            }
//            ids = new StringBuilder(ids.substring(0, ids.length() - 1));
//            //巡检详情
//            String sql2 = "select inspection_id,sum(case is_adaptation when 1 then score else 0 end) score,sum(case is_adaptation when 1 then template_score else 0 end) template_score from om_si_inspection_detail where app_id='" + user
//                    .getAppId() + "' and inspection_id in (" + ids + ") group by inspection_id ";
//
//            @SuppressWarnings("rawtypes")
//            //总分 以及 报告ID
////			List<HashMap> maps = JPAHelper.nativeQuery(sql2, null, HashMap.class);
//
//            String sql1 = "SELECT i.code,t.name as template_name,i.summary,i.shop_code,i.shop_name,"
//                    + "i.inspector_name,i.receiver_name,tmp.template_score sourceScore,tmp.score score,i.create_time,d.group_name,d.insp_element_name,"
//                    + "d.insp_element_Desc,di.comments,d.job_reveiver job_receiver ,d.is_adaptation,"
//                    + "d.score as eachScore ,d.template_score as eachSourceScore ,IFNULL(di.imgs,'') imgs,IFNULL(di.video,'') video ,d.is_one_vote_veto" +
//                    " FROM om_si_inspection_detail d "
//                    + "LEFT JOIN om_si_insp_detail_issue di on d.id =di.insp_detail_id "
//                    + "left JOIN om_si_inspection i ON i.id = d.inspection_id "
//                    + "left join om_si_template t on i.template_id = t.id "
//                    + "LEFT JOIN ( "+ sql2 +" ) tmp ON tmp.inspection_id = d.inspection_id "
//                    + "where d.app_id = '"+user.getAppId() +"'and d.inspection_id in (" + ids + ") ORDER BY d.create_time desc ,d.group_name";
//            List<InspectionExportDto> nativeQuery = JPAHelper.nativeQuery(sql1, null, InspectionExportDto.class);
//            for (InspectionExportDto dto : nativeQuery) {
//                //视频素材处理
//                if(StringUtils.isNotBlank(dto.getVideo()) && StringOperatorUtils.stringContains(dto.getVideo()) ){
//                    dto.setVideo(StringOperatorUtils.stringReplace(dto.getVideo()));
//                }
//            }
//            condition.setData(nativeQuery);
////			excel 导出
//            String[] fieldNames = {"code", "template_name", "summary", "shop_code", "shop_name", "inspector_name", "receiver_name", "create_time",
//                    "score","sourceScore", "scoreRate", "group_name", "insp_element_name", "insp_element_desc", "comments", "job_receiver", "is_adaptation",
//                    "eachSourceScore","eachScore","imgs","imgs","imgs","imgs","imgs","imgs","imgs","imgs","imgs","video","is_one_vote_veto"};
//            ExportExcelCellType[] fieldTypes = null;
//            if(StringUtils.isNotBlank(inspectionId)){
//                fieldTypes = new ExportExcelCellType[]{
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
////							图片
//                        ExportExcelCellType.IMG_URL,
//                        ExportExcelCellType.IMG_URL,
//                        ExportExcelCellType.IMG_URL,
//                        ExportExcelCellType.IMG_URL,
//                        ExportExcelCellType.IMG_URL,
//                        ExportExcelCellType.IMG_URL,
//                        ExportExcelCellType.IMG_URL,
//                        ExportExcelCellType.IMG_URL,
//                        ExportExcelCellType.IMG_URL,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        //一票否决项
//                        ExportExcelCellType.RICH_TEXT_STRING,
////							url
////							ExportExcelCellType.LINK_URL,
////							ExportExcelCellType.LINK_URL,
////							ExportExcelCellType.LINK_URL,
////							ExportExcelCellType.LINK_URL,
////							ExportExcelCellType.LINK_URL,
////							ExportExcelCellType.LINK_URL,
////							ExportExcelCellType.LINK_URL,
////							ExportExcelCellType.LINK_URL,
////							ExportExcelCellType.LINK_URL,
//                };
//            }else{
//                fieldTypes = new ExportExcelCellType[]{
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        ExportExcelCellType.RICH_TEXT_STRING,
///*//							图片
//							ExportExcelCellType.IMG_URL,
//							ExportExcelCellType.IMG_URL,
//							ExportExcelCellType.IMG_URL,
//							ExportExcelCellType.IMG_URL,
//							ExportExcelCellType.IMG_URL,
//							ExportExcelCellType.IMG_URL,
//							ExportExcelCellType.IMG_URL,
//							ExportExcelCellType.IMG_URL,
//							ExportExcelCellType.IMG_URL,*/
////							url
//                        ExportExcelCellType.LINK_URL,
//                        ExportExcelCellType.LINK_URL,
//                        ExportExcelCellType.LINK_URL,
//                        ExportExcelCellType.LINK_URL,
//                        ExportExcelCellType.LINK_URL,
//                        ExportExcelCellType.LINK_URL,
//                        ExportExcelCellType.LINK_URL,
//                        ExportExcelCellType.LINK_URL,
//                        ExportExcelCellType.LINK_URL,
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                        //一票否决项
//                        ExportExcelCellType.RICH_TEXT_STRING,
//                };
//            }
//            int length = fieldNames.length;
//            FieldControlObject[] fieldControlObjects = new FieldControlObject[length];
//
//            //			查询
//            Map<String, String> args = new HashMap<>();
//            args.put("accessToken",accessToken );
//            String rev = ShopGuideService.post("/api/common/queryUserIdAndName", null, args);
//            JsonObject json = JSonHelper.getJsonObject(rev);
//            if (json == null) {
//                throw new RatelRuntimeException("调用用户接口失败！");
//            }
//            String code = JSonHelper.getJsonMemberValue_String(json, "code");
//            String msg = JSonHelper.getJsonMemberValue_String(json, "msg");
//            if (!code.equals("200")) {
//                throw new RatelRuntimeException("调用用户接口失败。" + msg);
//            }
//            HashMap groupUser = (HashMap) JSonHelper.getJsonMemberValue_Object(json, "value",HashMap.class);
//
//            for (int i = 0; i < length; i++) {
//                FieldControlObject fieldControlObject = new FieldControlObject(fieldNames[i], fieldTypes[i]);
//                if (i > 19 ) {
//                    fieldControlObject.setIndex(i - 19);
//                }
//                if(i == 10 ){
//                    fieldControlObject.setFormatterOfBean(new FormatterOfBean() {
//                        @Override
//                        public <T> String format(Object value, Field[] fields, T t) {
//                            Field score = ExportExcelUtils.getFieldByName(fields, "score");
//                            Field sourceScore = ExportExcelUtils.getFieldByName(fields, "sourceScore");
//                            String  r= "0.00%";
//                            try {
//                                if(score != null && sourceScore != null){
//                                    String score1 = (String) score.get(t);
//                                    String sourceScore2 = (String) sourceScore.get(t);
//                                    DecimalFormat df = new DecimalFormat("0.00%");
//                                    if (StringUtils.isNotEmpty(sourceScore2)){
//                                        r = df.format(Float.parseFloat(score1)/Float.parseFloat(sourceScore2));
//                                    }
//                                }
//                            } catch (IllegalAccessException e) {
//                                throw new RatelRuntimeException("计算得分率失败");
//                            }
//                            return r;
//                        }
//                    });
//
//                }
//                //处理接受者
//                if(i == 15 ){
//                    fieldControlObject.setFormatterOfBean(new FormatterOfBean() {
//                        @Override
//                        public <T> String format(Object value, Field[] fields, T t) {
//                            return (String) groupUser.getOrDefault((String)value,(String)value);
//                        }
//                    });
//
//                }
//                fieldControlObjects[i] = fieldControlObject;
//            }
//            condition.setFieldControlObjects(fieldControlObjects);
//            //导出
//        }
//        System.out.println("使用前可用内存" +Runtime.getRuntime().freeMemory()/ 1024/1024);
//        ExportExcelUtils.exportOneSheet(sheetName, condition, response);
//        System.out.println("使用后可用内存" +Runtime.getRuntime().freeMemory()/ 1024/1024);
//
//    }
//
//
//    public void inspectionExportNew(String accessToken, UserInfo user, String keyword, Long inspectorId, Long shopId, String dateBegin,
//                                    String dateEnd, Integer loginType, String inspector,String inspectionId, String shop, Long areaId, Long agentId,Long companyId, Long departId, HttpServletResponse response) throws Exception {
//        // UserInfo user = Cache.getValue(token);
//        String sql = String.format(
//                "select m.*,t.total_score sourceScore from om_si_inspection m left join om_si_template t on m.template_id=t.id where m.delete_flag=0 and m.app_id=? ");
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        // 2018.5.3，特步要求在管理端查看巡店报告时，查看有店铺权限的报告。
//        if (loginType != null && loginType.equals(1)) {
//            String condition = " 1=1 ";
//            if (!PrimitiveUtils.isNullLong(areaId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(areaId, "") + "_4,%'";
//            }
//            if (!PrimitiveUtils.isNullLong(departId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(departId, "") + "_3,%'";
//            }
//            if (!PrimitiveUtils.isNullLong(agentId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(agentId, "") + "_6,%'";
//            }
//            if (!PrimitiveUtils.isNullLong(companyId, true)) {
//                condition += " and o.path like '%," + PrimitiveUtils.convertToString(companyId, "") + "_2,%'";
//            }
//            sql += " and " + getAuthShopString(accessToken, "m.shop_id", condition);
//        } else {
//            // if (loginType == null || !loginType.equals(1)) {
//            // 默认情况下只有巡检人或报告接收人可以看。
////			sql += " and (m.inspector_id=? or receiver_id=?";
////			paras.add(user.getSysUserId());
////			paras.add(user.getSysUserId());
//
//            // 2017.10.18 增加店铺内所有人都可以看巡店报告。
//            // 2017.12.8 去掉店铺条件。
//            // if (user.getBelongOrg() != null) {
//            // sql += " or m.shop_id=?";
//            // // 如果传了店铺ID就取传入的，否则取当前用户的店铺ID。
//            // if (!PrimitiveUtils.isNullLong(shopId, true))
//            // paras.add(shopId);
//            // else
//            // paras.add(user.getBelongOrg());
//            // }
//
//            if (!PrimitiveUtils.isNullLong(shopId, true)) {
//                sql += " or m.shop_id=?";
//                paras.add(shopId);
//            }
//
////			sql += ")";
//        }
//
//        if (!PrimitiveUtils.isNullString(keyword)) {
//            sql += " and (m.name like ? or m.code like ? or t.`name` like ?)";
//            paras.add("%" + keyword + "%");
//            paras.add("%" + keyword + "%");
//            paras.add("%" + keyword + "%");
//        }
//
//        if (!PrimitiveUtils.isNullLong(inspectorId, true)) {
//            sql += " and m.inspector_id=?";
//            paras.add(inspectorId);
//        }
//
//        if (!PrimitiveUtils.isNullString(inspector)) {
//            sql += " and m.inspector_name like ?";
//            paras.add("%" + inspector + "%");
//        }
//
//        if (!PrimitiveUtils.isNullString(inspectionId)) {
//            sql += " and m.id = ?";
//            paras.add(inspectionId);
//        }
//
//        if (!PrimitiveUtils.isNullString(shop)) {
//            sql += " and (m.shop_name like ? or m.shop_code like ?)";
//            paras.add("%" + shop + "%");
//            paras.add("%" + shop + "%");
//        }
//
//        if (!PrimitiveUtils.isNullString(dateBegin)) {
//            Date begin = PrimitiveUtils.convertToDate(dateBegin.replaceAll("'", ""), null);
//            if (begin != null) {
//                sql += " and m.create_time>=?";
//                paras.add(PrimitiveUtils.convertDateToString(begin, "yyyy-MM-dd") + " 0:0:0");
//            }
//        }
//
//        if (!PrimitiveUtils.isNullString(dateEnd)) {
//            Date end = PrimitiveUtils.convertToDate(dateEnd.replaceAll("'", ""), null);
//            if (end != null) {
//                sql += " and m.create_time<=?";
//                paras.add(PrimitiveUtils.convertDateToString(end, "yyyy-MM-dd") + " 23:59:59");
//            }
//        }
//
//        // 如果有shopid，就强制只取店铺id的。
//        if (!PrimitiveUtils.isNullLong(shopId, true)) {
//            sql += " and m.shop_id=?";
//            paras.add(shopId);
//        }
//
//        sql += " order by m.update_time desc";
//
//        //所需要的所有id
//        List<InspectionEntity> result = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class);
//
//        if (CollectionUtils.isNotEmpty(result)) {
//            StringBuilder ids = new StringBuilder();
//
//            //分批次获取数据，每一万份报告为一个sheet
//            List<List<InspectionEntity>> list = ListUtil.createList(result, 10000);
//            //如果写到不同的sheet 不同的对象
//            ExcelWriter excelWriter = null;
//            try {
//                // 这里 指定文件
//                excelWriter = EasyExcel.write(response.getOutputStream()).build();
//
//                System.out.println("使用前可用内存" +Runtime.getRuntime().freeMemory()/ 1024/1024);
//                for (int i = 0; i < list.size(); i++) {
//                    List<InspectionEntity> subList = list.get(i);
//                    ids = new StringBuilder();
//
//                    for (InspectionEntity inspection : subList) {
//                        ids.append("'").append(inspection.id).append("',");
//                    }
//                    ids = new StringBuilder(ids.substring(0, ids.length() - 1));
//                    //巡检详情
//                    String sql2 = "select inspection_id,sum(case is_adaptation when 1 then score else 0 end) score,sum(case is_adaptation when 1 then template_score else 0 end) template_score from om_si_inspection_detail where app_id='" + user
//                            .getAppId() + "' and inspection_id in (" + ids + ") group by inspection_id ";
//
//                    String sql1 = "SELECT i.code,t.name as template_name,i.summary,i.shop_code,i.shop_name,"
//                            + "i.inspector_name,i.receiver_name,tmp.template_score sourceScore,tmp.score score,i.create_time,d.group_name,d.insp_element_name,"
//                            + "d.insp_element_Desc,di.comments,d.job_reveiver job_receiver ,d.is_adaptation,"
//                            + "d.score as eachScore ,d.template_score as eachSourceScore ,IFNULL(di.imgs,'') imgs,IFNULL(di.video,'') video ,d.is_one_vote_veto" +
//                            " FROM om_si_inspection_detail d "
//                            + "LEFT JOIN om_si_insp_detail_issue di on d.id =di.insp_detail_id "
//                            + "left JOIN om_si_inspection i ON i.id = d.inspection_id "
//                            + "left join om_si_template t on i.template_id = t.id "
//                            + "LEFT JOIN ( " + sql2 + " ) tmp ON tmp.inspection_id = d.inspection_id "
//                            + "where d.app_id = '" + user.getAppId() + "'and d.inspection_id in (" + ids + ") ORDER BY d.create_time desc ,d.group_name";
//                    List<InspectionExportDto> nativeQuery = JPAHelper.nativeQuery(sql1, null, InspectionExportDto.class);
//                    for (InspectionExportDto dto : nativeQuery) {
//                        //视频素材处理
//                        if (StringUtils.isNotBlank(dto.getVideo()) && StringOperatorUtils.stringContains(dto.getVideo())) {
//                            dto.setVideo(StringOperatorUtils.stringReplace(dto.getVideo()));
//                        }
//                    }
//
//                    //todo 使用新模式
//                    ArrayList<InspectionExportExcelDto> demoDataList = new ArrayList<>();
//                    nativeQuery.forEach(exportDto -> {
//                        InspectionExportExcelDto demoData = InspectionExportExcelDto.builder()
//                                .code(exportDto.getCode())
//                                .templateName(exportDto.getTemplate_name())
//                                .summary(exportDto.getSummary())
//                                .shopCode(exportDto.getShop_code())
//                                .shopName(exportDto.getShop_name())
//                                .inspectorName(exportDto.getInspector_name())
//                                .receiverName(exportDto.getReceiver_name())
//                                .createTime(exportDto.getCreate_time())
//                                .score(exportDto.getScore())
//                                .sourceScore(exportDto.getSourceScore())
//                                .groupName(exportDto.getGroup_name())
//                                .inspElementName(exportDto.getInsp_element_name())
//                                .inspElementDesc(exportDto.getInsp_element_desc())
//                                .comments(exportDto.getComments())
//                                .jobReceiver(exportDto.getJob_receiver())
//                                .isAdaptation(exportDto.getIs_adaptation())
//                                .eachSourceScore(exportDto.getEachSourceScore())
//                                .eachScore(exportDto.getEachScore())
//                                .isOneVoteVeto(exportDto.getIs_one_vote_veto())
//                                .build();
//
//                        //得分率
//                        String scoreRate = "";
//                        if (StringUtils.isNotBlank(exportDto.getScore()) && StringUtils.isNotBlank(exportDto.getSourceScore())) {
//                            float score = Float.parseFloat(exportDto.getScore());
//                            float sourceScore = Float.parseFloat(exportDto.getSourceScore());
//                            if (sourceScore != 0.0f) {
//                                scoreRate = score / sourceScore * 100 + "%";
//                            } else {
//                                scoreRate = "0%";
//                            }
//                        } else {
//                            scoreRate = "0%";
//                        }
//                        demoData.setScoreRate(scoreRate);
//
//                        //图片
//                        String imgStr = exportDto.getImgs();
//                        if (StringUtils.isNotBlank(imgStr)) {
//                            String[] imgs = imgStr.split(",");
//                            for (int j = 0; j < imgs.length; j++) {
//                                String img = imgs[j];
//                                if (j + 1 == 1) {
//                                    demoData.setImg1(img);
//                                } else if (j + 1 == 2) {
//                                    demoData.setImg2(img);
//                                } else if (j + 1 == 3) {
//                                    demoData.setImg3(img);
//                                } else if (j + 1 == 4) {
//                                    demoData.setImg4(img);
//                                } else if (j + 1 == 5) {
//                                    demoData.setImg5(img);
//                                } else if (j + 1 == 6) {
//                                    demoData.setImg6(img);
//                                } else if (j + 1 == 7) {
//                                    demoData.setImg7(img);
//                                } else if (j + 1 == 8) {
//                                    demoData.setImg8(img);
//                                } else if (j + 1 == 9) {
//                                    demoData.setImg9(img);
//                                }
//                            }
//                        }
//
//                        //视频
//                        String video = exportDto.getVideo();
//                        if (StringUtils.isNotBlank(video)) {
//                            demoData.setVideo(video);
//                        }
//
//                        demoDataList.add(demoData);
//                    });
//                    //设置返回头
//                    response.setContentType("application/ms-excel;charset=UTF-8");//设置类型
//                    response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("filename.xlsx", "UTF-8"))));
//
////					response.setHeader("Pragma", "No-cache");//设置头
////					response.setHeader("Cache-Control", "no-cache");//设置头
////					response.setDateHeader("Expires", 0);//设置日期头
//
//                    // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。InspectionExportExcelDto.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
//                    WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheet" + (i + 1))
//                            .head(InspectionExportExcelDto.class)
//                            .registerWriteHandler(new InspectionExportExcelDtoWriteHandler()).build();
//                    // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
//                    excelWriter.write(demoDataList, writeSheet);
//
//                }
//
//                System.out.println("使用后可用内存" +Runtime.getRuntime().freeMemory()/ 1024/1024);
//            } finally {
//                // 千万别忘记finish 会帮忙关闭流
//                if (excelWriter != null) {
//                    excelWriter.finish();
//                }
//            }
//        }
//    }
//
//
//
//
//
//    private String getAuthShopString(String accessToken, String fieldName, String condition) throws Exception {
//        List<Object> lists = ShopGuideService.queryOrgByNameOrCode(accessToken, "5", null, condition, null, 1, 1000);
//		/*if(lists.size()>=10000) {
//			List<Object> listsarg =null;
//			int i=2;
//			for(;;) {
//				listsarg = ShopGuideService.queryOrgByNameOrCode(accessToken, "5", null, condition, null, i, 10000);
//				lists.addAll(listsarg);
//				i++;
//				if(listsarg.size()>=10000) {
//					break;
//				}
//			}
//		}*/
//        if (lists == null || lists.size() == 0) {
//            // 没有店铺权限。
//            return " 1=2";
//        } else {
//            if(lists.size()>=1000){
//                return " 1=1 ";
//            }else{
//                StringBuilder shopids = new StringBuilder();
//                for (Object object : lists) {
//                    @SuppressWarnings("unchecked")
//                    Map<String, Object> shop = (Map<String, Object>) object;
//                    Long sid = PrimitiveUtils.convertToLong(shop.get("orgId"), null);
//                    if (!PrimitiveUtils.isNullLong(sid, true)) {
//                        shopids.append(PrimitiveUtils.convertToString(sid, "") + ",");
//                    }
//                }
//                shopids.delete(shopids.length() - 1, shopids.length());
//                return " " + fieldName + " in (" + shopids.toString() + ")";
//                // paras.add("(" + shopids.toString() + ")");
//            }
//        }
//    }
//
//    public InspectionEntity inspectionDetailQuery(String accessToken, UserInfo user, String id) throws Exception {
//        // UserInfo user = Cache.getValue(token);
//        String sql = String.format("select m.* from om_si_inspection m where m.app_id=? and m.id=?");
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        paras.add(id);
//        List<InspectionEntity> list = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class);
//        if (list == null || list.size() != 1) {
//            throw new RatelRuntimeException("未找到巡检表！");
//        }
//
//        InspectionEntity insp = list.get(0);
//        if (insp.deleteFlag) {
//            throw new RatelRuntimeException("巡检报告已经删除！");
//        }
//        insp.buildDetails();
//        insp.buildWatchers();
//
//        List<?> shopInfo = getShopInfo(accessToken, user, insp.shopId);
//        if (shopInfo != null && !shopInfo.isEmpty()) {
//            @SuppressWarnings("unchecked")
//            Map<String, Object> shop = (Map<String, Object>) shopInfo.get(0);
//            insp.shopCode = PrimitiveUtils.convertToString(shop.get("code"), insp.shopCode);
//            insp.shopName = PrimitiveUtils.convertToString(shop.get("name"), insp.shopName);
//            insp.shopAddress = PrimitiveUtils.convertToString(shop.get("address"), insp.shopAddress);
//        }
//        return insp;
//    }
//
//    public InspectionEntity inspectionDetailShowQuery(String accessToken, UserInfo user, String id) throws Exception {
//        // String sql = String.format("select m.* from om_si_inspection m where m.app_id=? and m.id=?");
//        String sql = String.format("select m.*,t.name template_name from om_si_inspection m join om_si_template t on t.id=m.template_id where m.id=?");
//        List<Object> paras = new ArrayList<Object>();
//        // paras.add(user.getAppId());
//        paras.add(id);
//        List<InspectionEntity> list = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class);
//        // if (list == null || list.size() != 1)
//        // throw new RatelRuntimeException("未找到巡检表！");
//
//        if (list == null || list.size() == 0) {
//            return null;
//        }
//
//        InspectionEntity insp = list.get(0);
//        if (insp.deleteFlag) {
//            throw new RatelRuntimeException("100001", "巡店报告已被删除！");
//        }
//
//        insp.buildGroupDetails();
//        insp.buildWatchers();
//
//        // List<?> shopInfo = getShopInfo(accessToken, user, insp.shopId);
//        // if (shopInfo != null && !shopInfo.isEmpty()) {
//        // @SuppressWarnings("unchecked")
//        // Map<String, Object> shop = (Map<String, Object>) shopInfo.get(0);
//        // insp.shopCode = PrimitiveUtils.convertToString(shop.get("code"), insp.shopCode);
//        // insp.shopName = PrimitiveUtils.convertToString(shop.get("name"), insp.shopName);
//        // insp.shopAddress = PrimitiveUtils.convertToString(shop.get("address"), insp.shopAddress);
//        // }
//        return insp;
//    }
//
//    public InspectionEntity inspectionDetailShowQueryNew(String accessToken, UserInfo user, String id) throws Exception {
//        String sql = String.format("select m.*,t.name template_name from om_si_inspection m join om_si_template t on t.id=m.template_id where m.id=?");
//        List<Object> paras = new ArrayList<>();
//        paras.add(id);
//        List<InspectionEntity> list = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class);
//        if (CollectionUtils.isEmpty(list)){
//            return null;
//        }
//
//        InspectionEntity insp = list.get(0);
//        if (insp.deleteFlag) {
//            throw new RatelRuntimeException("100001", "巡店报告已被删除！");
//        }
//        insp.buildGroupDetailsNew();
//        insp.buildWatchers();
//        return insp;
//    }
//
//    /**
//     * 获取巡店模板
//     *
//     * @param shopId
//     */
//    public PageResult<?> getInspForm(UserInfo user, Long shopId, Integer pageIndex, Integer pageSize ,String token) throws Exception {
//        String sql = "select distinct s.*,t.temp_usage from om_si_template_org m join om_si_template_main s on m.template_root_id=s.template_root_id " +
//                " join om_si_template t on t.id=s.template_id" + " where s.delete_flag=0 and s.published_status=1 and m.app_id=? and ? like CONCAT('%',m.org_path_short,'%')";
//
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        paras.add(user.getOrgPath());
//
//        sql += " order by s.update_time desc";
//
//        //这个表中没有数据, 无效的查询
//        PageResult<?> result = JPAHelper.nativeQuery(sql, paras, InspectFormMainEntity.class, pageIndex, pageSize);
//        if (result != null && result.data != null) {
//            sql = "select t.template_root_id,t.report_count from om_si_inspection i join om_si_template t on i.template_id=t.id" +
//                    " where i.shop_id=? and i.inspector_id=? order by i.create_time desc limit 1";
//            paras = new ArrayList<Object>();
//            paras.add(shopId);
//            paras.add(user.getSysUserId());
//            @SuppressWarnings("rawtypes")
//            List<HashMap> lasts = JPAHelper.nativeQuery(sql, paras, HashMap.class);
//            if (lasts != null && lasts.size() > 0) {
//                String lastTempId = PrimitiveUtils.convertToString(lasts.get(0).get("template_root_id"), "");
//                @SuppressWarnings("unchecked")
//                List<InspectFormMainEntity> lists = (List<InspectFormMainEntity>) result.data;
//                for (InspectFormMainEntity row : lists) {
//                    if (PrimitiveUtils.equalsString(lastTempId, row.templateRootId, true)) {
//                        lists.remove(row);
//                        lists.add(0, row);
//                        break;
//                    }
//                }
//            }
//        } else {
//            result = getInspFormFromShop(user, shopId, pageIndex, pageSize,token);
//        }
//        return result;
//    }
//
//    @RatelDoc("旧的")
//    public PageResult<?> getInspFormFromShop(UserInfo user, Long shopId, Integer pageIndex, Integer pageSize ,String token) throws Exception {
//        PageResult<InspectFormMainEntity> result = InspectFormMainEntity.getTempLateMainByShopIdAndAppId(shopId,user.getAppId(),pageIndex,pageSize);
//        if (result != null && result.data != null) {
//            //根据token 到超导库中查询该用户所属的组
//            String groupUser = getGroupUser(token);
//
//            //根据当前巡检表集合，获取巡检表所适用的店铺
//            @SuppressWarnings("unchecked")
//            List<InspectFormMainEntity> lists2 = (List<InspectFormMainEntity>) result.data;
//
//            //收集巡检表所有templateRootId
//            List<String> templateRootIdList = lists2.stream().map(x -> x.templateRootId).collect(Collectors.toList());
//
//            //根据templateRootId查询所有用户组集合
//            String join = StringUtils.join(templateRootIdList, "','");
//            String userGroupSql = " select g.* from om_si_template_user_group g where g.template_root_id in ('"+ join +"') ";
//            List<InspectFormUserGroupEntity> userGroupLists= JPAHelper.nativeQuery(userGroupSql,new ArrayList<Object>(), InspectFormUserGroupEntity.class);
//            Map<Object, List<InspectFormUserGroupEntity>> stringListHashMap = Optional.ofNullable(userGroupLists).orElseGet(ArrayList::new).stream().collect(Collectors.groupingBy(x -> x.templateRootId));
////			HashMap<String, List<InspectFormUserGroupEntity>> stringListHashMap = new HashMap<>();
////			if (CollectionUtils.isNotEmpty(userGroupLists)){
////				//根据templateRootId对所有用户组集合进行分组
////				for (InspectFormUserGroupEntity ins : userGroupLists) {
////					String templateRootId = ins.templateRootId;
////					if (stringListHashMap.containsKey(templateRootId)){
////						//该分组已经存在
////						List<InspectFormUserGroupEntity> insList = stringListHashMap.get(templateRootId);
////						insList.add(ins);
////					}else{
////						//该分组不存在
////						List<InspectFormUserGroupEntity> insList = new ArrayList<>();
////						insList.add(ins);
////						stringListHashMap.put(templateRootId,insList);
////					}
////				}
////			}
//
//            Iterator it = lists2.iterator();
//            //排除当前用户没有权限的巡检表
//            while (it.hasNext()) {
//                InspectFormMainEntity me = (InspectFormMainEntity) it.next();
//                List<InspectFormUserGroupEntity> ugList = stringListHashMap.get(me.templateRootId);
//                //如果用户组为空不处理，原因在于创建巡检表时，如果仅选择了适用店铺，没有选择使用用户组，则默认适用与所有用户组
//                if (CollectionUtils.isNotEmpty(ugList)) {
//                    //	用户在用户组里不处理
//                    boolean userGroupFlag = false;
//                    for (InspectFormUserGroupEntity ug : ugList) {
//                        if (groupUser.contains(String.valueOf(ug.userGroupId))) {
//                            userGroupFlag = true;
//                            break;
//                        }
//                    }
//                    if (!userGroupFlag) {
//                        it.remove();
//                    }
//                }
//            }
//
//            //下面做了什么事呢？
//            //用户已经使用过的巡检表 排在巡检表的第一位（表：上报表，条件：店铺id,用户id）
//            String sql = "select t.template_root_id,t.report_count from om_si_inspection i join om_si_template t on i.template_id=t.id" +
//                    " where i.shop_id=? and i.inspector_id=? order by i.create_time desc limit 1";
//            List paras = new ArrayList<Object>();
//            paras.add(shopId);
//            paras.add(user.getSysUserId());
//            @SuppressWarnings("rawtypes")
//            List<HashMap> lasts = JPAHelper.nativeQuery(sql, paras, HashMap.class);
//            if (lasts != null && lasts.size() > 0) {
//                //为什么只取结果的第一个呢
//                String lastTempId = PrimitiveUtils.convertToString(lasts.get(0).get("template_root_id"), "");
//                @SuppressWarnings("unchecked")
//                List<InspectFormMainEntity> lists = (List<InspectFormMainEntity>) result.data;
//                for (InspectFormMainEntity row : lists) {
//                    if (PrimitiveUtils.equalsString(lastTempId, row.templateRootId, true)) {
//                        lists.remove(row);
//                        lists.add(0, row);
//                        break;
//                    }
//                }
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 根据token 到超导库中查询该用户所属的组
//     * @param token
//     * @return
//     * @throws Exception
//     */
//    private String getGroupUser(String token) throws Exception {
//        Map<String, String> args = new HashMap<>();
//        args.put("accessToken", token);
//        String rev = ShopGuideService.post("/api/common/queryGroupByToken", null, args);
//        JsonObject json = JSonHelper.getJsonObject(rev);
//        if (json == null) {
//            throw new RatelRuntimeException("调用用户接口失败！");
//        }
//        String code = JSonHelper.getJsonMemberValue_String(json, "code");
//        String msg = JSonHelper.getJsonMemberValue_String(json, "msg");
//        if (!code.equals("200")) {
//            throw new RatelRuntimeException("调用用户接口失败。" + msg);
//        }
//        String groupUser = JSonHelper.getJsonMemberValue_String(json, "value");
//        return groupUser;
//    }
//
//    public DataTable getInspRelativeShop(UserInfo user, String keyword) {
//        String sql = "select DISTINCT shop_id,shop_name,shop_address from om_si_inspection where app_id=? and inspector_id=? and shop_id is not null";
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        paras.add(user.getSysUserId());
//        if (!PrimitiveUtils.isNullString(keyword)) {
//            sql += " and (shop_name like ? or shop_address like ?)";
//            paras.add("%" + keyword + "%");
//            paras.add("%" + keyword + "%");
//        }
//
//        sql += " order by id desc limit 20";
//
//        DataTable lists = JPAHelper.nativeQuery(sql, paras);
//
//        return lists;
//    }
//
//    /**
//     * 和巡店报告中保持一致，直接接口/api/getstoreuserlist。这个接口中处理过岗位的名称，但是没有头像
//     *
//     * @param accessToken
//     * @param user
//     * @param shopId
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     * @throws Exception
//     */
//    public PageResult<?> getInspShopMemeberOld(String accessToken, UserInfo user, Long shopId, Integer pageIndex, Integer pageSize) throws Exception {
//        Map<String, String> args = new HashMap<>();
//        args.put("accessToken", accessToken);
//        args.put("store", shopId.toString());
//        if (!PrimitiveUtils.isNullInteger(pageIndex, true))
//            args.put("p", pageIndex.toString());
//        if (!PrimitiveUtils.isNullInteger(pageSize, true))
//            args.put("r", pageSize.toString());
//        args.put("fields", "sys_user_id,user_code,name,user_post,head_pic");
//        // userPost; //岗位 0:总部,1:区域经理,2:导购,3:督导,4:店长
//
//        String rev = ShopGuideService.post("/api/bms/getUsersByStore", null, args);
//        // List<?> list = ShopGuideService.parseArrayResultFromJson(rev, ArrayList.class);
//        JsonObject json = ShopGuideService.parseJsonObjectFromJson(rev);
//        json = JSonHelper.getJsonMemberValue_Object(json, "value");
//        List<?> list = (List<?>) JSonHelper.getJsonMemberValue_Object(json, "users", ArrayList.class);
//
//        PageResult<?> pageResult = new PageResult<>();
//        List<ShopUserInfo> users = new ArrayList<>();
//        if (list != null && list.size() > 0) {
//            for (Object obj : list) {
//                @SuppressWarnings("unchecked")
//                Map<String, Object> row = (Map<String, Object>) obj;
//                ShopUserInfo ui = new ShopUserInfo(row.get("sys_user_id"), row.get("user_code"), row.get("name"), row.get("user_post"), row.get("head_pic"));
//                users.add(ui);
//            }
//            pageResult.pageSize = list.size();
//        }
//        pageResult.data = users;
//        Integer count = JSonHelper.getJsonMemberValue_Integer(json, "totalrows");
//        pageResult.counts = count;
//        return pageResult;
//    }
//
//    public PageResult<?> getInspShopMemeber(String accessToken, UserInfo user, Long shopId, Integer pageIndex, Integer pageSize) throws Exception {
//        Map<String, String> args = new HashMap<>();
//        args.put("accessToken", accessToken);
//        args.put("storeid", shopId.toString());
//        if (!PrimitiveUtils.isNullInteger(pageIndex, true)) {
//            args.put("page", pageIndex.toString());
//        }
//        if (!PrimitiveUtils.isNullInteger(pageSize, true)) {
//            args.put("rows", pageSize.toString());
//        }
//
//        String rev = ShopGuideService.post("/api/getstoreuserlist", null, args);
//        // List<?> list = ShopGuideService.parseArrayResultFromJson(rev, ArrayList.class);
//        JsonObject json = ShopGuideService.parseJsonObjectFromJson(rev);
//        // json = JSonHelper.getJsonMemberValue_Object(json, "dataObject");
//        @SuppressWarnings("unchecked")
//        List<Map<String, Object>> shops = (List<Map<String, Object>>) JSonHelper.getJsonMemberValue_List(json, "dataObject");
//
//        PageResult<?> pageResult = new PageResult<>();
//        List<ShopUserInfo> users = new ArrayList<>();
//        int count = 0;
//        if (shops != null && shops.size() > 0) {
//            count = shops.size();
//            for (int i = 0; i < count; i++) {
//                Map<String, Object> row = shops.get(i);
//                ShopUserInfo ui = new ShopUserInfo(row.get("sysUserId"), row.get("user_code"), row.get("sysUsername"), row.get("user_post"), row.get("headPic"));
//                ui.user_post_name = PrimitiveUtils.convertToString(row.get("postName"), "");
//                users.add(ui);
//            }
//        }
//        pageResult.data = users;
//        pageResult.counts = count;
//        return pageResult;
//    }
//
//    public List<?> getShopInfo(String accessToken, UserInfo user, Long shopId) throws Exception {
//        Map<String, String> args = new HashMap<>();
//        args.put("accessToken", accessToken);
//        args.put("store", shopId.toString());
//
//        String rev = ShopGuideService.post("/api/bms/getStoreViewInfo", null, args);
//
//        List<?> list = ShopGuideService.parseArrayResultFromJson(rev, "value", ArrayList.class);
//        return list;
//    }
//
//    public List<?> getInspShopIssue(UserInfo user, Long shopId, Integer status) {
//        return null;
//    }
//
//    /**
//     * 这个接口似曾相识，总感觉看到了好多遍
//     * @param accessToken
//     * @param user
//     * @param keyword：店铺名称/店铺地址
//     * @return
//     * @throws Exception
//     */
//    public DataTable getInspectionShop(String accessToken, UserInfo user, String keyword) throws Exception {
//        String sql = "select DISTINCT shop_id,shop_name from om_si_inspection where app_id=? and shop_id is not null and shop_name is not null";
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        if (!PrimitiveUtils.isNullString(keyword)) {
//            sql += " and (shop_name like ? or shop_address like ?)";
//            paras.add("%" + keyword + "%");
//            paras.add("%" + keyword + "%");
//        }
//
//        sql += " and " + getAuthShopString(accessToken, "shop_id", null);
//
//        sql += " group by shop_id order by id desc limit 100";
//
//        DataTable lists = JPAHelper.nativeQuery(sql, paras);
//
//        return lists;
//    }
//
//    public DataTable getInspectionInspector(String accessToken, UserInfo user, String keyword) throws Exception {
//        String sql = "select DISTINCT inspector_id,inspector_name from om_si_inspection where app_id=? and inspector_id is not null and inspector_name is not null";
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        if (!PrimitiveUtils.isNullString(keyword)) {
//            sql += " and (inspector_name like ?)";
//            paras.add("%" + keyword + "%");
//        }
//
//        sql += " and " + getAuthShopString(accessToken, "shop_id", null);
//
//        sql += " group by inspector_id order by id desc limit 100";
//
//        DataTable lists = JPAHelper.nativeQuery(sql, paras);
//
//        return lists;
//    }
//
//    public void assignShopguideJob(String accessToken, List<assignJob> jobs) throws Exception {
//        try {
//            if (CollectionUtils.isEmpty(jobs)) {
//                return;
//            }
//            //校验店外人员数量
//            jobs.forEach(x ->{
//                List<String> strings = Arrays.asList(x.outSider.split(","));
//                if (CollectionUtils.isNotEmpty(strings) && strings.size() > 50){
//                    throw new RatelRuntimeException("店外邀请人员上限50个!");
//                }
//            });
//
//            Map<String, String> args = new HashMap<>();
//            args.put("accessToken", accessToken);
//
//            String rev = null;
//            JsonObject json = null;
//            // 2017.9.28:周彬加了根据typeName自动生成类型的判断。如果名称有自动取id，没有则自动生成。
//            // //
//            // rev = ShopGuideService.post("/api/cruiseshop/config/getCategoryList", null, args);
//            // json = ShopGuideService.parseJsonObjectFromJson(rev);
//            // @SuppressWarnings("unchecked")
//            // List<Map<String, Object>> typeInfos = (List<Map<String, Object>>) JSonHelper.getJsonMemberValue_Object(json, "dataObject", ArrayList.class);
//            // for (Map<String, Object> list : typeInfos) {
//            // String typeName = list.get("typeName").toString();
//            // Long ltid = PrimitiveUtils.convertToLong(list.get("typeId"), 0L);
//            // String typeId = ltid.toString();
//            //
//            // for (assignJob job : jobs) {
//            // if (job.typeNames.equalsIgnoreCase(typeName)) {
//            // if (job.typeIds == null || job.typeIds.isEmpty() || job.typeIds.equals("0"))
//            // job.typeIds = typeId;
//            // }
//            // }
//            // }
//
//            args.put("tasks", JSonHelper.toJSon(jobs));
//            rev = ShopGuideService.post("/api/open/cruiseshop/scenetask/batchCreate", null, args);
//
//            try {
//                json = ShopGuideService.parseJsonObjectFromJson(rev);
//            } catch (Exception e) {
//                Logger.error(this, "调用接口任务失败：" + e.toString());
//                Logger.error(this, "调用接口任务失败：accessToken：" + accessToken);
//                Logger.error(this, "调用接口任务失败：tasks：" + JSonHelper.toJSon(jobs));
//                Logger.error(this, "调用接口任务失败：返回字符串：" + rev);
//                throw e;
//            }
//
//            @SuppressWarnings("unchecked")
//            List<List<Object>> taskIds = (List<List<Object>>) JSonHelper.getJsonMemberValue_Object(json, "dataObject", ArrayList.class);
//            if (taskIds == null) {
//                Logger.error(this, rev);
//                return;
//            }
//
//            String sql = "update om_si_inspection_detail set job_task_id=? where id=?";
//            List<List<Object>> sqlPara = new ArrayList<>();
//            for (List<Object> rows : taskIds) {
//                // taskid\sourceid\sourcedetailid
//                String taskId = PrimitiveUtils.convertToLong(rows.get(0), 0L).toString();
//                String detailId = rows.get(2).toString();
//
//                List<Object> paras = new ArrayList<>();
//                paras.add(taskId);
//                paras.add(detailId);
//                sqlPara.add(paras);
//            }
//
//            JPAHelper.nativeExecutePrepareBatch(sql, sqlPara);
//        } catch (Exception ex) {
//            // 此处没有事务，就不回滚了，有问题可以从超导库手动处理。
//            Logger.error(this, "巡检表批量生成任务失败：" + ex.toString());
//            throw ex;
//        }
//    }
//
//    public InspectionEntity patrolShopTaskReissue(String accessToken, String code, UserInfo user) throws Exception{
//        //巡店报告
//        String sql = String.format("select m.* from om_si_inspection m  where m.code= ? ");
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(code);
//        List<InspectionEntity> list = JPAHelper.nativeQuery(sql, paras, InspectionEntity.class);
//        if (list == null || list.size() == 0) {
//            return null;
//        }
//        InspectionEntity insp = list.get(0);
//        //报告检查项的明细
//        String detailSql = String.format("select m.* from om_si_inspection_detail m  where m.inspection_id= ? ");
//        List<Object> detailParas = new ArrayList<Object>();
//        detailParas.add(insp.id);
//        List<InspectionDetailEntity> detailList = JPAHelper.nativeQuery(detailSql, detailParas, InspectionDetailEntity.class);
//        //检查项对应的问题
//        List<assignJob> job = new ArrayList<>();
//        if(CollectionUtils.isNotEmpty(detailList)){
//            for (InspectionDetailEntity detailEntity : detailList) {//巡检项对应的问题
//                //查询该巡店报告下面所有问题
//                String issueSql = "select * from om_si_insp_detail_issue where insp_detail_id = ?";
//                List<Object> issueParam = new ArrayList<Object>();
//                issueParam.add(detailEntity.id);
//                List<HashMap> issuemaps = JPAHelper.nativeQuery(issueSql, issueParam, HashMap.class);
//                List<InspectionDetailIssueEntity> issueList = ModelHelper.convertMapToModel(issuemaps, InspectionDetailIssueEntity.class);
//                if(CollectionUtils.isNotEmpty(issueList)){
//                    for (InspectionDetailIssueEntity issueEntity : issueList) {
//                        String questionDecs = issueEntity.comments;
//                        if(questionDecs==null||"".equals(questionDecs)) {
//                            questionDecs = detailEntity.inspElementName;
//                        }
//                        if(StringUtils.isNotBlank(issueEntity.video) && StringOperatorUtils.stringContains(issueEntity.video) ){
//                            issueEntity.video=StringOperatorUtils.stringReplace(issueEntity.video);
//                        }
//                        //判断字符串jobReceiver 是否是null
//                        if(issueEntity.jobReceiver==null){
//                            issueEntity.jobReceiver=0L;
//                        }
//                        //任务截至时间，获取优先级  问题 > 报告 > null
//                        Date feedbackDeadline = detailEntity.feedbackDeadline != null ? detailEntity.feedbackDeadline : insp.feedbackDeadline;
//                        //添加保存店外人员字段outSider
//                        if ((StringUtils.isNotBlank(issueEntity.outSider))||(issueEntity.jobReceiver != null && issueEntity.jobReceiver.longValue() != 0)){
//                            job.add(new assignJob(insp.id, detailEntity.id, insp.shopId, issueEntity.jobReceiver, questionDecs, issueEntity.imgs, detailEntity.jobTypeIds, detailEntity.groupName, "", "1",issueEntity.video,issueEntity.outSider,issueEntity.id,feedbackDeadline));
//                        }
//                    }
//                }
//            }
//        }
//        if(CollectionUtils.isNotEmpty(job)){
//            // 发送任务接口。
//            assignShopguideJob(accessToken, job);
//        }
//        return insp;
//    }
//
//
//    public PageResult<?> previewOmSiInspactions(Long inspector, String started, String ended, Integer pageIndex, Integer pageSize) {
//
//        String sql = String.format("select m.* from om_si_inspection m  where create_time > ? and create_time <= ? and inspector_id=? order by m.create_time desc ");
//        List<Object> paras = new ArrayList<Object>();
//
//        Date begin = PrimitiveUtils.convertToDate(started.replaceAll("'", ""), null);
//        if (begin != null) {
//            started = PrimitiveUtils.convertDateToString(begin, "yyyy-MM-dd HH:mm:ss");
//        }
//
//        Date end = PrimitiveUtils.convertToDate(ended.replaceAll("'", ""), null);
//        if (end != null) {
//            ended = PrimitiveUtils.convertDateToString(end, "yyyy-MM-dd HH:mm:ss");
//        }
//
//        paras.add(started);
//        paras.add(ended);
//        paras.add(inspector);
//
//        return JPAHelper.nativeQuery(sql, paras, InspectionEntity.class, pageIndex, pageSize);
//    }
//
//    public PageResult<?> displayOmSiInspactions(Long inspector, Long assignee, String started, String ended, Integer pageIndex, Integer pageSize) {
//
//        String sql = "select * from om_si_inspection  where create_time > ? and create_time <= ? and inspector_id=? and origin_inspector=? order by create_time desc ";
//        List<Object> paras = new ArrayList<Object>();
//
//        Date begin = PrimitiveUtils.convertToDate(started.replaceAll("'", ""), null);
//        if (begin != null) {
//            started = PrimitiveUtils.convertDateToString(begin, "yyyy-MM-dd HH:mm:ss");
//        }
//
//        Date end = PrimitiveUtils.convertToDate(ended.replaceAll("'", ""), null);
//        if (end != null) {
//            ended = PrimitiveUtils.convertDateToString(end, "yyyy-MM-dd HH:mm:ss");
//        }
//
//        paras.add(started);
//        paras.add(ended);
//        paras.add(assignee);
//        paras.add(inspector);
//
//        return JPAHelper.nativeQuery(sql, paras, InspectionEntity.class, pageIndex, pageSize);
//    }
//
//    public List<UserPosition> getHistoryReportor(UserInfo user, String userName,String telPhone,Integer pageIndex, Integer pageSize, String accessToken) throws Exception {
//        String sql = "select *from om_si_report_receiver  where inviter=? GROUP BY receiver_id order by create_time desc ";
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getSysUserId());
//        @SuppressWarnings("rawtypes")
//        PageResult result =JPAHelper.nativeQuery(sql, paras, InspectionReportReceiver.class,pageIndex, pageSize);
//        List<UserPosition>	userPositionList=null;
//        if (result != null && result.data != null) {
//            @SuppressWarnings("unchecked")
//            List<InspectionReportReceiver> rows = (List<InspectionReportReceiver>) result.data;
//            StringBuffer userIds=new StringBuffer();
//            for (int i = 0; i < rows.size(); i++) {
//                InspectionReportReceiver receiver=rows.get(i);
//                if(receiver!=null && StringUtils.isNotBlank(receiver.receiverId)){
//                    userIds.append(receiver.receiverId).append(",");
//                }
//            }
//            if(StringUtils.isNotBlank(userIds.toString())){
//                userPositionList=userBasicInfoShopguideJob(accessToken,userIds.toString().substring(0, userIds.toString().length()-1),userName,telPhone);
//            }
//        }
//        return userPositionList;
//    }
//
//    public int updateOmSiInspectionByTime(String accessToken, Long inspector, Long assignee, String started, String ended) throws Exception {
//        int result = 0;
//        try {
//            if (inspector == null || assignee == null || started == null || ended == null) {
//                return result;
//            }
//            List<Object> paras = new ArrayList<Object>();
//
//            Date begin = PrimitiveUtils.convertToDate(started.replaceAll("'", ""), null);
//            if (begin != null) {
//                started = PrimitiveUtils.convertDateToString(begin, "yyyy-MM-dd HH:mm:ss");
//            }
//
//            Date end = PrimitiveUtils.convertToDate(ended.replaceAll("'", ""), null);
//            if (end != null) {
//                ended = PrimitiveUtils.convertDateToString(end, "yyyy-MM-dd HH:mm:ss");
//            }
//            String sql = "update om_si_inspection set inspector_id=? , origin_inspector=? where create_time > ? and create_time <= ? and inspector_id=?";
//
//            paras.add(assignee);
//            paras.add(inspector);
//            paras.add(started);
//            paras.add(ended);
//            paras.add(inspector);
//            result = JPAHelper.nativeExecute(sql, paras);
//        } catch (Exception ex) {
//            Logger.error(this, "更新巡店报告拥有者失败：" + ex.toString());
//        }
//        return result;
//    }
//
//    public void deleteShopguideJob(String accessToken, HashMap<String, InspectionDetailEntity> jobs) throws Exception {
//        if (jobs == null || jobs.size() == 0)
//            return;
//
//        Map<String, String> args = new HashMap<>();
//        args.put("accessToken", accessToken);
//
//        String rev = null;
//
//        String ids = "[";
//        for (String key : jobs.keySet()) {
//            if (!PrimitiveUtils.isNullString(key))
//                ids += key + ",";
//        }
//        if (ids.indexOf(",") >= 0)
//            ids = ids.substring(0, ids.length() - 1);
//        ids += "]";
//
//        if (PrimitiveUtils.equalsString(ids, "[]", true))
//            return;
//
//        args.put("taskIds", ids);
//        rev = ShopGuideService.post("/api/cruiseshop/scenetask/batchDel", null, args);
//
//        try {
//            ShopGuideService.parseJsonObjectFromJson(rev);
//        } catch (Exception e) {
//            Logger.error(this, "调用接口任务失败：" + e.toString());
//            Logger.error(this, "调用接口任务失败：accessToken：" + accessToken);
//            Logger.error(this, "调用接口任务失败：taskIds：" + JSonHelper.toJSon(jobs));
//            Logger.error(this, "调用接口任务失败：返回字符串：" + rev);
//            throw e;
//        }
//    }
//
//    public boolean getHasInspAddAuth(UserInfo user) {
//        return true;
//		/*String userAppid = user.getAppId();
//		boolean rev = false;
//		String sql = "select code,parm_value from md_sysparams where code='sys_insp_testappid'";
//		List<?> list = JPAHelper.nativeQueryList(sql, null);
//		if (list != null && list.size() > 0) {
//			Object[] row = (Object[]) list.get(0);
//			Object oids = row[1];
//			if (oids != null) {
//				String idst = oids.toString();
//				String[] ids = idst.split(",");
//				if (ids != null && ids.length > 0) {
//					for (String id : ids) {
//						if (id.equalsIgnoreCase(userAppid)) {
//							return true;
//						}
//					}
//				}
//			}
//		}
//
//		return rev;*/
//    }
//
//    /**
//     * 该方法最后会获得一个 更新字符串
//     * @param accessToken
//     * @param user
//     * @param baiduMapKey
//     * @param appId
//     * @param supPaths
//     * @param keyword
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    public String importGPSFromShopAddress(String accessToken, UserInfo user, String baiduMapKey, String appId, String supPaths, String keyword) throws Exception {
//        if (!user.getAppId().equals(appId)) {
//            throw new RatelRuntimeException("登录用户AppId和传入AppId不符！");
//        }
//        // String sql = "select sys_org_id,name,province,city,county,address,longitude,latitude from sys_org_shop where app_id=? and delete_flag=0";
//        // List<Object> paras = new ArrayList<Object>();
//        // paras.add(user.getAppId());
//        // @SuppressWarnings("rawtypes")
//        // List<HashMap> shops = JPAHelper.nativeQuery(sql, paras, HashMap.class);
//        // if (shops == null || shops.isEmpty())
//        // return null;
//
//        Map<String, String> args = new HashMap<>();
//        args.put("accessToken", accessToken);
//        if (!PrimitiveUtils.isNullString(keyword)) {
//            args.put("name", keyword);
//        }
//        if (!PrimitiveUtils.isNullString(supPaths)) {
//            args.put("supPaths", supPaths);
//        }
//        String shop_rev = ShopGuideService.post("/api/web/org/getAuthShop", null, args);
//
//        JsonObject shop_json = JSonHelper.getJsonObject(shop_rev);
//        JsonObject shop_jsonData = JSonHelper.getJsonMemberValue_Object(shop_json, "dataObject");
//        List<Map<String, Object>> shops = (List<Map<String, Object>>) JSonHelper.getJsonMemberValue_List(shop_jsonData, "list");
//
//        StringBuilder sb = new StringBuilder();
//        // http://api.map.baidu.com/place/v2/search?q=福建泉州解放路中闽百汇LA&region=全国&output=json&ak=pWRElKlZWOwYgbZMlNqHuebv78rpRmxY
//        String urlbase = "http://api.map.baidu.com/place/v2/search?q=%s&region=全国&output=json&ak=%s";
//        for (Map<String, Object> row : shops) {
//            Long shopId = PrimitiveUtils.convertToLong(row.get("sysOrgId"), null);
//            if (shopId == null) {
//                continue;
//            }
//
//            String shopName = row.get("name").toString();
//            String url = String.format(urlbase, shopName, baiduMapKey);
//            String rev = HttpClientHelper.post_Json(url, "", "");
//
//            JsonObject json = JSonHelper.getJsonObject(rev);
//            String message = JSonHelper.getJsonMemberValue_String(json, "message");
//            if (!message.equals("ok")) {
//                Logger.error(this, message);
//            }
//            JsonArray gpsInfos = JSonHelper.getJsonMemberValue_Array(json, "results");
//            if (gpsInfos == null || gpsInfos.isJsonNull() || gpsInfos.size() <= 0) {
//                continue;
//            }
//            JsonObject gps = (JsonObject) gpsInfos.get(0);
//            JsonObject location = JSonHelper.getJsonMemberValue_Object(gps, "location");
//            if (location == null || location.isJsonNull()) {
//                continue;
//            }
//            String lat = JSonHelper.getJsonMemberValue_String(location, "lat");
//            String lng = JSonHelper.getJsonMemberValue_String(location, "lng");
//
//            sb.append("/*" + shopName + "*/\r\n");
//            sb.append(String.format("update sys_org_shop set longitude='%s',latitude='%s' where sys_org_id=%s;\r\n", lng, lat, shopId));
//        }
//        return sb.toString();
//    }
//
//
//    /**
//     * 刷库
//     * 这个方法做了一件事，就是给上报得详情添加接收人名称
//     * 方法中涉及循环调用超导库，查询用户详情，为上报详情赋予接收人名称
//     * 疑问：为什么不在添加上报详情得时候，直接添加接收人名称呢？
//     * @param accessToken
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    @Transactional
//    public String flushOnlyOne(String accessToken) throws Exception {
//
//        String sql = String.format("select * from om_si_inspection_detail ");
//        List<Object> paras = new ArrayList<Object>();
//        List<InspectionDetailEntity> list = JPAHelper.nativeQuery(sql, paras, InspectionDetailEntity.class);
//        for (InspectionDetailEntity inspectionDetailEntity : list) {
//            String id = inspectionDetailEntity.id;
//            Long jobReceiverid = inspectionDetailEntity.jobReceiver;
//            if(jobReceiverid==null) {
//                continue;
//            }
//            Map<String, String> args = new HashMap<>();
//            args.put("accessToken",accessToken );
//            args.put("sysUserId",jobReceiverid+"");
////			args.put("sysUserId",314679339+"");
////			args.put("sysUserId",315161464+"");
////			args.put("sysUserId",314679339+"");
//            System.out.println(jobReceiverid+"开始执行");
//            String rev = ShopGuideService.post("/api/web/user/detail1", null, args);
//            JsonObject json = JSonHelper.getJsonObject(rev);
//            if (json == null) {
//                throw new RatelRuntimeException("调用用户接口失败！");
//            }
//            String code = JSonHelper.getJsonMemberValue_String(json, "code");
//            if (!code.equals("102")) {
//                throw new RatelRuntimeException("调用用户接口失败。");
//            }
////			JsonArray dataObject = JSonHelper.getJsonMemberValue_Array(json,"dataObject");
//            JsonElement dataObject1 = JSonHelper.getJsonMemberValue(json, "dataObject");
//            if(dataObject1.isJsonNull()){
//                continue;
//            }
//            Object dataObject = JSonHelper.getJsonMemberValue_Object(json, "dataObject", HashMap.class);
//            if(dataObject==null) {
//                continue;
//            }
//            Map map = (Map) JSonHelper.getJsonMemberValue_Object(json,"dataObject", HashMap.class);
//            String name = map.get("name")+"";
//
//            String updatesql = "update om_si_inspection_detail set jname= ? where id= ? ";
//            List<Object> parass = new ArrayList<>();
//            parass.add(name);
//            parass.add(id);
//            System.out.println(String.format("更新数据：om_si_inspection_detail.job_reveiver_name = %s,om_si_inspection_detail.id = %s",name,id));
//            JPAHelper.nativeExecute(updatesql, parass);
//        }
//
//
//
////		String shop_rev = ShopGuideService.post("/api/web/org/getAuthShop", null, args);
////
////		JsonObject shop_json = JSonHelper.getJsonObject(shop_rev);
////		JsonObject shop_jsonData = JSonHelper.getJsonMemberValue_Object(shop_json, "dataObject");
////		List<Map<String, Object>> shops = (List<Map<String, Object>>) JSonHelper.getJsonMemberValue_List(shop_jsonData, "list");
////
////		StringBuilder sb = new StringBuilder();
////		for (Map<String, Object> row : shops) {
////			Long shopId = PrimitiveUtils.convertToLong(row.get("sysOrgId"), null);
////			if (shopId == null)
////				continue;
////
////			String shopName = row.get("name").toString();
////			String url = String.format(urlbase, shopName, baiduMapKey);
////			String rev = HttpClientHelper.post_Json(url, "", "");
////
////			JsonObject json = JSonHelper.getJsonObject(rev);
////			String message = JSonHelper.getJsonMemberValue_String(json, "message");
////			if (!message.equals("ok")) {
////				Logger.error(this, message);
////			}
////			JsonArray gpsInfos = JSonHelper.getJsonMemberValue_Array(json, "results");
////			if (gpsInfos == null || gpsInfos.isJsonNull() || gpsInfos.size() <= 0)
////				continue;
////			JsonObject gps = (JsonObject) gpsInfos.get(0);
////			JsonObject location = JSonHelper.getJsonMemberValue_Object(gps, "location");
////			if (location == null || location.isJsonNull())
////				continue;
////			String lat = JSonHelper.getJsonMemberValue_String(location, "lat");
////			String lng = JSonHelper.getJsonMemberValue_String(location, "lng");
////
////			sb.append("/*" + shopName + "*/\r\n");
////			sb.append(String.format("update sys_org_shop set longitude='%s',latitude='%s' where sys_org_id=%s;\r\n", lng, lat, shopId));
////		}
//        return null;
//    }
//
//    private final String INSP_DISTANCE = "om_si_inspection_distance";
//
//    public String getInspectionPara(UserInfo user) {
//        String sql = "select parm_value from sys_params where app_id=? and parm_code=?";
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        paras.add(INSP_DISTANCE);
//
//        @SuppressWarnings("rawtypes")
//        List<HashMap> lists = JPAHelper.nativeQuery(sql, paras, HashMap.class);
//        if (lists != null && lists.size() > 0) {
//            return PrimitiveUtils.convertToString(lists.get(0).get("parm_value"), "300");
//        }
//
//        return "300";
//    }
//
//    public Integer setInspectionPara(UserInfo user, String value) {
//        if (PrimitiveUtils.isNullString(value))
//            throw new RatelRuntimeException("请传入距离！");
//        Integer dis = PrimitiveUtils.convertToInteger(value, null);
//        if (PrimitiveUtils.isNullInteger(dis, true))
//            throw new RatelRuntimeException("请传入有效的距离！");
//
//        String sql = "delete sys_params where app_id=? and parm_code=?";
//        List<Object> paras = new ArrayList<Object>();
//        paras.add(user.getAppId());
//        paras.add(INSP_DISTANCE);
//        JPAHelper.nativeExecute(sql, paras);
//
//        String uuid = UUID.randomUUID().toString();
//        sql = "insert into sys_params(id, parm_code, parm_desc, parm_value, app_id) VALUES(?,?,?,?,?)";
//        paras = new ArrayList<Object>();
//        paras.add(uuid);
//        paras.add(INSP_DISTANCE);
//        paras.add("巡店GPS距离");
//        paras.add(dis.toString());
//        paras.add(user.getAppId());
//        return JPAHelper.nativeExecute(sql, paras);
//    }
//
//    public InspectionEntity inspectPreviewSave(String accessToken, UserInfo user, InspectionEntity insp) throws Exception {
//        if (PrimitiveUtils.isNullString(insp.templateId)) {
//            throw new RatelRuntimeException("请指定巡检表模板");
//        }
//
//        InspTemplateSvc svc = InspTemplateCtl.getService();
//        TemplateEntity template = svc.templateDetail(user, insp.templateId);
//        Map<String, jobTypeInfo> answerScore = buildElementScoreMap(template);
//        inspectSave_Check(user, insp, template);
//
//        insp.id = insp.createNewId();
//        insp.inspectorId = user.getSysUserId();
//        insp.inspectorName = user.getName();
//
//        List<InspectionDetailEntity> details = insp.details;
//        List<InspectionWatcherEntity> watchers = insp.watchers;
//        if (details == null || details.isEmpty()) {
//            throw new RatelRuntimeException("请传入巡检明细！");
//        }
//
//        List<InspectionGroupDetailEntity> groupDetails = new ArrayList<InspectionGroupDetailEntity>();
//        InspectionGroupDetailEntity group = new InspectionGroupDetailEntity();
//        String gidOld = "";
//        Integer issueCount = 0;
//        Float totalScore = 0f;
//        for (InspectionDetailEntity detail : details) {
//            detail.id = detail.createNewId();
//            detail.inspectionId = insp.id;
//            jobTypeInfo typeInfo = getChoiceScore(answerScore, detail.inspElementId);
//            detail.templateScore = typeInfo.score;
//
//            // 增加保存冗余信息
//            detail.inspElementName = typeInfo.eleName;
//            detail.inspElementDesc = typeInfo.eleDesc;
//            detail.groupId = typeInfo.groupId;
//            detail.groupName = typeInfo.groupName;
//            detail.groupShowOrder = typeInfo.groupOrder;
//            detail.elementShowOrder = typeInfo.eleOrder;
//
//            if (!detail.isAdaptation) {
//                detail.score = null;
//            }
//
//            // 处理组。
//            // 虽然做了组的处理，但是组并没有保存，也没有从组上获取有效的数据
//            if (!PrimitiveUtils.equalsString(gidOld, detail.groupId, true)) {
//                group = new InspectionGroupDetailEntity();
//                group.name = detail.groupName;
//                group.detailCount = 0;
//                group.sourceScore = 0;
//                group.score = 0f;
//
//                gidOld = detail.groupId;
//            }
//            group.detailCount++;
//            group.sourceScore = group.sourceScore + PrimitiveUtils.convertToInteger(detail.templateScore, 0);
//            group.score = group.score + PrimitiveUtils.convertToInteger(detail.score, 0);
//            group.details.add(detail);
//
//            // detail = (InspectionDetailEntity) detail.Save(user);
//            if (!detail.isAdaptation) {
//                continue;
//            }
//
//            totalScore = totalScore + PrimitiveUtils.convertToInteger(detail.score, 0);
//
//            if (detail.isAssignJob) {
//                if (detail.jobReceiver == null || detail.jobReceiver.longValue() == 0) {
//                    throw new RatelRuntimeException("未指定任务接收人");
//                }
//
//                issueCount++;
//            }
//        }
//        if (watchers != null && !watchers.isEmpty()) {
//            for (InspectionWatcherEntity watcher : watchers) {
//                watcher.id = watcher.createNewId();
//                watcher.inspectionId = insp.id;
//                watcher = (InspectionWatcherEntity) watcher.Save(user);
//            }
//        }
//        insp.score = totalScore;
//        insp.issueCount = issueCount;
//        insp.code = insp.buildCode(user);
//        // InspectionEntity inspNew = (InspectionEntity) insp.Save(user);
//        InspectionEntity inspNew = insp;
//        inspNew.groupDetails = groupDetails;
//
//        // 重算下分。
//        inspNew.sourceScore = 0f;
//        for (InspectionGroupDetailEntity g : inspNew.groupDetails) {
//            if (PrimitiveUtils.isNullFloat(g.score, true)) {
//                continue;
//            }
//
//            inspNew.sourceScore += g.score;
//        }
//
//        // 缓存10分钟
//        Cache.setValue("inspectionDetailPreview_id_" + inspNew.id, inspNew, 10 * 60);
//
//        return inspNew;
//    }
//
//    public InspectionEntity inspectionDetailPreviewShowQuery(String accessToken, UserInfo user, String id) throws Exception {
//        InspectionEntity entity = Cache.getValue("inspectionDetailPreview_id_" + id);
//        return entity;
//    }
//
//    public List<InspectionEntity> getInspectionEntityList(String accessToken, UserInfo user,String startTime,String endTime,String editStartTime,String editEndTime,Integer pageIndex, Integer pageSize) throws Exception {
//        if(pageIndex==null || pageIndex==0){
//            pageIndex=1;
//        }
//        if((pageSize==null || pageSize==0 ) || pageSize>100){
//            pageSize=100;
//        }
//        List<Object> params = new ArrayList<Object>();
//        String sql = "select * from om_si_inspection   where app_id = ? ";
//        params.add(user.getAppId());
//
//        String sqlTimeFilter = concatCreateAndEditTimeSql(params, startTime, endTime, editStartTime, editEndTime);
//
//        if(StringUtils.isNotBlank(sqlTimeFilter)){
//            sql += sqlTimeFilter;
//        }
//
//        sql+=" ORDER BY create_time ASC ";
//        sql += " limit " + (pageIndex - 1) * pageSize + "," + pageSize ;
//        return JPAHelper.nativeQuery(sql, params, InspectionEntity.class);
//    }
//
//    public List<TemplateEntity> getTemplateEntityList(String accessToken, UserInfo user,String startTime,String endTime,Integer pageIndex, Integer pageSize) {
//        if(pageIndex==0 || pageIndex==null){
//            pageIndex=1;
//        }
//        if((pageSize==0 || pageSize==null) || pageSize>100){
//            pageSize=100;
//        }
//        List<Object> params = new ArrayList<Object>();
//        String sql = "select * from om_si_template   where app_id = ? ";
//        params.add(user.getAppId());
//        if(StringUtils.isNotBlank(startTime)){
//            sql+=" AND  create_time > ? ";
//            params.add(startTime);
//        }
//        if(StringUtils.isNotBlank(endTime)){
//            sql+=" AND  create_time < ? ";
//            params.add(endTime);
//        }
//        sql+=" ORDER BY create_time ASC ";
//        sql += " limit " + (pageIndex - 1) * pageSize + "," + pageSize ;
//
//        return JPAHelper.nativeQuery(sql, params, TemplateEntity.class);
//    }
//
//    public List<InspectionDetailEntity> getInspectionDetailEntityList(String accessToken, UserInfo user,String startTime,String endTime,String editStartTime, String editEndTime,Integer pageIndex, Integer pageSize) {
//        if(pageIndex==null || pageIndex==0 ){
//            pageIndex=1;
//        }
//        if((pageIndex==null || pageSize==0) || pageSize>100){
//            pageSize=100;
//        }
//        List<Object> params = new ArrayList<Object>();
//        String sql = "select * from om_si_inspection_detail   where app_id = ?  ";
//        params.add(user.getAppId());
//
//        String sqlTimeFilter = concatCreateAndEditTimeSql(params, startTime, endTime, editStartTime, editEndTime);
//        if(StringUtils.isNotBlank(sqlTimeFilter)){
//            sql += sqlTimeFilter;
//        }
//
//        sql+=" ORDER BY create_time ASC ";
//        sql += " limit " + (pageIndex - 1) * pageSize + "," + pageSize ;
//
//        return JPAHelper.nativeQuery(sql, params, InspectionDetailEntity.class);
//    }
//
//    public List<InspectionDetailIssueEntity> getInspectionDetailIssueEntityList(String accessToken, UserInfo user,String startTime,String endTime,String editStartTime, String editEndTime,Integer pageIndex, Integer pageSize) {
//        if(pageIndex==null || pageIndex==0 ){
//            pageIndex=1;
//        }
//        if((pageIndex==null || pageSize==0) || pageSize>100){
//            pageSize=100;
//        }
//        List<Object> params = new ArrayList<Object>();
//        String sql = "select * from om_si_insp_detail_issue   where app_id = ?  ";
//        params.add(user.getAppId());
//
//        String sqlTimeFilter = concatCreateAndEditTimeSql(params, startTime, endTime, editStartTime, editEndTime);
//        if(StringUtils.isNotBlank(sqlTimeFilter)){
//            sql += sqlTimeFilter;
//        }
//
//        sql+=" ORDER BY create_time ASC ";
//        sql += " limit " + (pageIndex - 1) * pageSize + "," + pageSize ;
//        return JPAHelper.nativeQuery(sql, params, InspectionDetailIssueEntity.class);
//    }
//
//    //太平鸟项目 获取巡店报告
//    public List<InspectionEntity> getInspectionByUserIds(String userIds,String templateId) {
//
//        if(StringUtils.isBlank(userIds)){
//            return null;
//        }
//
//        InspTemplateSvc tempSvc = InspTemplateCtl.getService();
//        TemplateEntity temp = tempSvc.getByTempRoot(templateId);
//        if(temp != null){
//            templateId = temp.id;
//        }
//
//        Date date=new Date();
//        Calendar calendar=Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//        String dateTime= format.format(calendar.getTime());
//
//        String startTime=dateTime +" "+"00:00:00";
//        String endTime=dateTime+" "+"23:59:59";
//        List<Object> params = new ArrayList<Object>();
//        String sql = "select * from om_si_inspection where delete_flag = 0 AND  create_time > ?  AND create_time < ? AND template_id =? ";
//        params.add(startTime);
//        params.add(endTime);
//        params.add(templateId);
//        if(StringUtils.isNotBlank(userIds)){
//            List<String> result = Arrays.asList(userIds.split(","));
//            if(CollectionUtils.isNotEmpty(result)) {
//                List<String> filterSql = new ArrayList<String>();
//                sql += " and ( ";
//                for (int i=0; i<result.size(); i++) {
//                    filterSql.add(" inspector_id = ? ");
//                    params.add(result.get(i));
//                }
//                sql += StringUtils.join(filterSql, " or ");
//                sql += ")";
//            }
//        }
//        return JPAHelper.nativeQuery(sql, params, InspectionEntity.class);
//    }
//
//    //获取巡店报告详情
//    public List<InspectionDetailEntity> getInspectionDetailByUserIds(String userIds,String templateId) {
//        if(StringUtils.isBlank(userIds)){
//            return null;
//        }
//        Date date=new Date();
//        Calendar calendar=Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//        String dateTime= format.format(calendar.getTime());
//
//        String startTime=dateTime +" "+"00:00:00";
//        String endTime=dateTime+" "+"23:59:59";
//        List<Object> params = new ArrayList<Object>();
//        /*String sql = "select * from om_si_inspection_detail where delete_flag = 0 AND  create_time > ?  AND create_time < ? ";*/
//        String sql = " select sid.*,si.template_id as templateId  from om_si_inspection si LEFT JOIN om_si_inspection_detail sid on si.id=sid.inspection_id " +
//                " where sid.delete_flag = 0 AND  sid.create_time > ?  AND sid.create_time < ? AND si.template_id= ? ";
//        params.add(startTime);
//        params.add(endTime);
//        params.add(templateId);
//        if(StringUtils.isNotBlank(userIds)){
//            List<String> result = Arrays.asList(userIds.split(","));
//            if(CollectionUtils.isNotEmpty(result)) {
//                List<String> filterSql = new ArrayList<String>();
//                sql += " and ( ";
//                for (int i=0; i<result.size(); i++) {
//                    filterSql.add(" sid.create_user = ? ");
//                    params.add(result.get(i));
//                }
//                sql += StringUtils.join(filterSql, " or ");
//                sql += ")";
//            }
//        }
//
//        return JPAHelper.nativeQuery(sql, params, InspectionDetailEntity.class);
//    }
//
//
//    //添加邀请店外人保存字段
//    class assignJob {
//        public assignJob(String sourceId, String sourceDetail, Long assignShop, Long assignUser, String taskDesc, String picUrls, String typeIds, String typeNames, String watchids,
//                         String type,String video, String outSider,String issueEntityId) {
//            this.source = sourceId;
//            this.sourceDetail = sourceDetail;
//            this.assignShop = assignShop.toString();
//            this.assignUser = assignUser.toString();
//            this.taskDesc = taskDesc;
//            this.picUrls = picUrls;
//            this.typeIds = typeIds;
//            this.typeName = typeNames;
//
//            if (!assignUser.toString().equals(watchids))
//                this.invitedPersons = watchids;
//
//            this.type = type;
//            this.video = video;
//            this.outSider=outSider;
//            this.issueEntityId=issueEntityId;
//        }
//
//        public assignJob(String sourceId, String sourceDetail, Long assignShop, Long assignUser, String taskDesc,
//                         String picUrls, String typeIds, String typeNames, String watchids,
//                         String type,String video, String outSider,String issueEntityId, Date feedbackDeadline) {
//            this.source = sourceId;
//            this.sourceDetail = sourceDetail;
//            this.assignShop = assignShop.toString();
//            this.assignUser = assignUser.toString();
//            this.taskDesc = taskDesc;
//            this.picUrls = picUrls;
//            this.typeIds = typeIds;
//            this.typeName = typeNames;
//
//            if (!assignUser.toString().equals(watchids))
//                this.invitedPersons = watchids;
//
//            this.type = type;
//            this.video = video;
//            this.outSider=outSider;
//            this.issueEntityId=issueEntityId;
//            this.feedbackDeadline = feedbackDeadline;
//        }
//
//        public String source;
//
//        public String sourceDetail;
//
//        public String assignShop;
//
//        public String assignUser;
//
//        public String taskDesc;
//
//        public String picUrls;
//
//        public String video;
//
//        public String outSider;
//
//        public String typeIds = "0";
//
//        public String typeName;
//
//        public String invitedPersons;
//
//        // 类型(0现场任务，1巡店现场任务)
//        public String type;
//
//        public String issueEntityId;
//        // 反馈截止时间
//        public Date feedbackDeadline;
//    }
//
//    class jobTypeInfo {
//        //		public jobTypeInfo(Integer score, String groupName) {
//        public jobTypeInfo(Float score, String groupName) {
//            this.groupName = groupName;
//            this.score = score;
//        }
//
//        //		public jobTypeInfo(Integer score, String groupName, String eName, String eDesc, String gid, Integer gorder, Integer eorder) {
//        public jobTypeInfo(Float score, String groupName, String eName, String eDesc, String gid, Integer gorder, Integer eorder) {
//            this.groupName = groupName;
//            this.score = score;
//
//            this.eleName = eName;
//            this.eleDesc = eDesc;
//            this.groupId = gid;
//
//            this.groupOrder = gorder;
//            this.eleOrder = eorder;
//        }
//
//        public String groupName;
//        //		public Integer score;
//        public Float score;
//
//        public String groupId;
//        public String eleName;
//        public String eleDesc;
//
//        public Integer groupOrder;
//        public Integer eleOrder;
//    }
//
//    class ShopUserInfo {
//        public ShopUserInfo(Object sys_user_id, Object user_code, Object name, Object user_post, Object head_pic) {
//            this.sys_user_id = PrimitiveUtils.convertToLong(sys_user_id, null);
//            this.user_code = PrimitiveUtils.convertToString(user_code, null);
//            this.name = PrimitiveUtils.convertToString(name, null);
//            this.user_post = PrimitiveUtils.convertToLong(user_post, null);
//            this.head_pic = PrimitiveUtils.convertToString(head_pic, null);
//            if (this.user_post != null) {
//                // userPost; //岗位 0:总部,1:区域经理,2:导购,3:督导,4:店长
//                if (this.user_post.equals(0l))
//                    this.user_post_name = "总部";
//                else if (this.user_post.equals(1l))
//                    this.user_post_name = "区域经理";
//                if (this.user_post.equals(2l))
//                    this.user_post_name = "导购";
//                if (this.user_post.equals(3l))
//                    this.user_post_name = "督导";
//                if (this.user_post.equals(4l))
//                    this.user_post_name = "店长";
//            }
//
//        }
//
//        public Long sys_user_id;
//        public String user_code;
//        public String name;
//        public Long user_post;
//        public String head_pic;
//
//        public String user_post_name;
//    }
//
//
//    /**
//     * 调用接口获取该用户Id集合基本信息
//     * @param accessToken
//     * @param userIds
//     * @param userName
//     * @param telPhone
//     * @return
//     * @throws Exception
//     */
//    public List<UserPosition> userBasicInfoShopguideJob(String accessToken,String userIds, String userName,String telPhone) throws Exception {
//        try {
//            Map<String, String> args = new HashMap<>();
//            args.put("accessToken", accessToken);
//            args.put("userIds", userIds);
//            args.put("userName", userName);
//            args.put("telPhone", telPhone);
//            String rev = null;
//            JsonObject json = null;
//            rev = ShopGuideService.post("/api/user/queryUserByUserIds", null, args);
//            try {
//                json = ShopGuideService.parseJsonObjectFromJson(rev);
//            } catch (Exception e) {
//                Logger.error(this, "调用接口获取该用户Id集合基本信息失败：" + e.toString());
//                Logger.error(this, "调用接口获取该用户Id集合基本信息：accessToken：" + accessToken);
//                Logger.error(this, "调用接口获取该用户Id集合基本信息失败败：shopId：" + JSonHelper.toJSon(args));
//                Logger.error(this, "调用接口获取该用户Id集合基本信息失败：返回字符串：" + rev);
//                throw e;
//            }
//            //将字符串转化为JSONArray
//            JsonArray jsonArray = json.getAsJsonArray("value");
//            Gson gson = new Gson();
//            List<UserPosition> userBeanList = new ArrayList<>();
//            if(jsonArray.size()>0){
//                for (JsonElement user : jsonArray) {
//                    //使用GSON，直接转成Bean对象
//                    UserPosition userBean = gson.fromJson(user, UserPosition.class);
//                    userBeanList.add(userBean);
//                }
//            }
//            return userBeanList;
//        } catch (Exception ex) {
//            // 此处没有事务，就不回滚了，有问题可以从超导库手动处理。
//            Logger.error(this, "获取该用户Id集合基本信息失败：" + ex.toString());
//        }
//        return null;
//    }
//
//    private String concatCreateAndEditTimeSql(List<Object> params, String startTime, String endTime, String editStartTime, String editEndTime){
//        String sqlCreateTime = "";
//        String sqlEditTime = "";
//        String sqlRtn="";
//        if(StringUtils.isNotBlank(startTime)){
//            sqlCreateTime += " create_time > ? ";
//            params.add(startTime);
//        }
//        if(StringUtils.isNotBlank(endTime)){
//            if(StringUtils.isNotBlank(sqlCreateTime)){
//                sqlCreateTime += " AND  create_time < ? ";
//            } else {
//                sqlCreateTime += " create_time < ? ";
//            }
//            params.add(endTime);
//        }
//        Date dateStart = null;
//        Date dateEnd = null;
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//        if(StringUtils.isNotBlank(editStartTime)){
//            try {
//                dateStart = format.parse(editStartTime);
//            } catch (ParseException e) {
//                throw new RatelRuntimeException("参数错误：更新起始时间格式不合法!");
//            }
//            sqlEditTime += " update_time > ?";
//            params.add(editStartTime);
//        }
//        if(StringUtils.isNotBlank(editEndTime)){
//            try {
//                dateEnd = format.parse(editEndTime);
//            } catch (ParseException e) {
//                throw new RatelRuntimeException("参数错误：更新截止时间格式不合法!");
//            }
//            if(StringUtils.isNotBlank(sqlCreateTime)){
//                sqlEditTime += " AND update_time < ?";
//            } else {
//                sqlEditTime += " update_time < ?";
//            }
//            params.add(editEndTime);
//        }
//        if(dateStart != null && dateEnd != null){
//            if(dateStart.after(dateEnd)){
//                throw new RatelRuntimeException("参数错误：更新起始时间超过更新截止时间!");
//            }
//            if(DateUtil.getWeeksBetweenTime(dateStart, dateEnd)>1){
//                throw new RatelRuntimeException("参数错误：更新起始和截止时间的间隔不可以超过一周!");
//            }
//        }
//        if(StringUtils.isNotBlank(sqlCreateTime)){
//            sqlRtn += "(" + sqlCreateTime + ")" ;
//        }
//        if(StringUtils.isNotBlank(sqlEditTime)){
//            if(StringUtils.isNotBlank(sqlRtn)){
//                sqlRtn += " OR (" + sqlEditTime + ")";
//            } else {
//                sqlRtn += " (" + sqlEditTime + ")";
//            }
//
//        }
//        if(StringUtils.isNotBlank(sqlRtn)){
//            sqlRtn = "AND (" + sqlRtn + ")";
//        }
//
//        return sqlRtn;
//    }
//}
