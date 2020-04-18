package org.zh.activiti.highlight;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.collections4.CollectionUtils;

public class StrokeBpmnService {
	public static void main(String[] args) throws Exception {
		//测试可以正常显示中文的图，但没有高亮
		/**
网上说还要修改DefaultProcessDiagramCanvas,DefaultProcessDiagramGenerator源码
if (labelGraphicInfo != null) {
processDiagramCanvas.drawLabel(sequenceFlow.getName(), labelGraphicInfo, false);
}else {
GraphicInfo lineCenter = getLineCenter(graphicInfoList);
processDiagramCanvas.drawLabel(sequenceFlow.getName(), lineCenter, false);
} 
		 */
		StrokeBpmnService s = new StrokeBpmnService();
		byte[] strokeBpmnArray = s.getProcessImage("");
		FileOutputStream out = new FileOutputStream("d:/bpmn.png");
		out.write(strokeBpmnArray);
		out.close();

	}

	public byte[] getProcessImage(String processInstanceId) throws Exception {

		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		HistoryService historyService = processEngine.getHistoryService();

		// 获取历史流程实例
//		HistoricProcessInstance historicProcessInstance = queryHistoricProcessInstance(processInstanceId);
//        if (historicProcessInstance == null) {
//            throw new Exception();
//        } else {
		// 获取流程定义
//            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
//                    .getProcessDefinition(historicProcessInstance.getProcessDefinitionId());

		
		
		
		
		String processDefinitionKey = "nextMonthTargetProcess";
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).singleResult();

		// 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
		List<HistoricActivityInstance> historicActivityInstanceList = historyService
				.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceId().desc().list();
		// 已执行的节点ID集合
		List<String> executedActivityIdList = new ArrayList<>();
		int index = 1;
		for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
			if (index == 1) {
				executedActivityIdList.add(activityInstance.getActivityId() + "#");
			} else {
				executedActivityIdList.add(activityInstance.getActivityId());
			}
			index++;
		}
		// 获取流程图图像字符流
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

		// 已执行flow的集和
		List<String> executedFlowIdList = getHighLightedFlows(bpmnModel, historicActivityInstanceList);

		ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration()
				.getProcessDiagramGenerator();
		InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList,
				executedFlowIdList, "黑体", "黑体", "黑体", null, 1.0);

		byte[] buffer = new byte[imageStream.available()];
		imageStream.read(buffer);
		imageStream.close();
		return buffer;
	}
//    }

	/**
	 * 获取已经流转的线
	 *
	 * @param bpmnModel
	 * @param historicActivityInstances
	 * @return
	 */
	private static List<String> getHighLightedFlows(BpmnModel bpmnModel,
			List<HistoricActivityInstance> historicActivityInstances) {
		// 高亮流程已发生流转的线id集合
		List<String> highLightedFlowIds = new ArrayList<>();
		// 全部活动节点
		List<FlowNode> historicActivityNodes = new ArrayList<>();
		// 已完成的历史活动节点
		List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

		for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
			FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess()
					.getFlowElement(historicActivityInstance.getActivityId(), true);
			historicActivityNodes.add(flowNode);
			if (historicActivityInstance.getEndTime() != null) {
				finishedActivityInstances.add(historicActivityInstance);
			}
		}

		FlowNode currentFlowNode = null;
		FlowNode targetFlowNode = null;
		// 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
		for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
			// 获得当前活动对应的节点信息及outgoingFlows信息
			currentFlowNode = (FlowNode) bpmnModel.getMainProcess()
					.getFlowElement(currentActivityInstance.getActivityId(), true);
			List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

			/**
			 * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转：
			 * 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
			 * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
			 */
			if ("parallelGateway".equals(currentActivityInstance.getActivityType())
					|| "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
				// 遍历历史活动节点，找到匹配流程目标节点的
				for (SequenceFlow sequenceFlow : sequenceFlows) {
					targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(),
							true);
					if (historicActivityNodes.contains(targetFlowNode)) {
						highLightedFlowIds.add(targetFlowNode.getId());
					}
				}
			} else {
				List<Map<String, Object>> tempMapList = new ArrayList<>();
				for (SequenceFlow sequenceFlow : sequenceFlows) {
					for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
						if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
							Map<String, Object> map = new HashMap<>();
							map.put("highLightedFlowId", sequenceFlow.getId());
							map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
							tempMapList.add(map);
						}
					}
				}

				if (!CollectionUtils.isEmpty(tempMapList)) {
					// 遍历匹配的集合，取得开始时间最早的一个
					long earliestStamp = 0L;
					String highLightedFlowId = null;
					for (Map<String, Object> map : tempMapList) {
						long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
						if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
							highLightedFlowId = map.get("highLightedFlowId").toString();
							earliestStamp = highLightedFlowStartTime;
						}
					}

					highLightedFlowIds.add(highLightedFlowId);
				}

			}

		}
		return highLightedFlowIds;
	}
}
