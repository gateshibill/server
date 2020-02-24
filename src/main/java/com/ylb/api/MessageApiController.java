//package com.ylb.api;
//
//import org.apache.ibatis.annotations.Param;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ylb.entity.Message;
//import com.ylb.service.MessageService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
///**
// * 消息api
// * @author wxh
// *
// */
//@Api(tags="消息类相关接口")
//@RestController
//@CrossOrigin
//@RequestMapping("/api/message")
//public class MessageApiController {
//	@Autowired
//	private MessageService messageService;
//	/**
//	 * 获取我的消息
//	 * @param message
//	 * @return
//	 */
//	@ApiOperation(value="获取我的消息接口")
//	@ApiImplicitParams({
//		@ApiImplicitParam(name="message",value="消息对象",required=true,dataType="Message"),
//		@ApiImplicitParam(name="page",value="当前页数",required=false,dataType="Integer"),
//		@ApiImplicitParam(name="limit",value="每页条数",required=false,dataType="Integer")
//	})
//	@RequestMapping("/getMessageByUserId")
//	public String getMessageByUserId(Message message,Integer page,Integer limit) {
//		String result = messageService.getMessageList(message, page, limit);
//		return result;
//	}
//	/**
//	 * 获取messageId
//	 * @param messageId
//	 * @return
//	 */
//	@ApiOperation(value="获取消息详情接口")
//	@ApiImplicitParam(name="messageId",value="消息id",required=true,dataType="Integer")
//	@RequestMapping(value="/getMessageById",method=RequestMethod.POST)
//	public String getMessageById(Integer messageId) {
//		return messageService.getMessageById(messageId);
//	}
//	
//	/**
//	 * 批量删除消息
//	 * @param messageId
//	 * @return
//	 */
//	@ApiOperation(value="批量删除消息")
//	@ApiImplicitParam(name="messageId",value="消息id",required=true,dataType="String")
//	@RequestMapping(value="/delMessagelist",method=RequestMethod.POST)
//	public String delMessageList(String messageId) {
//		return messageService.delMessageList(messageId);
//	}
//	
//	/**
//	 * 获取未读信息
//	 * @param userId
//	 * @return
//	 */
//	@ApiOperation(value="获取未读信息")
//	@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String")
//	@RequestMapping(value="/getMessageCount",method=RequestMethod.POST)
//	public String getMessageCount(String userId) {
//		return messageService.messageCount(userId);
//	}
//	
//	/**
//	 * 修改状态为已读
//	 * @param messageId
//	 * @return
//	 */
//	@ApiOperation(value="修改状态为已读")
//	@ApiImplicitParam(name="messageId",value="消息id",required=true,dataType="Integer")
//	@RequestMapping(value="/updateMessage",method=RequestMethod.POST)
//	public String updateMessage(Integer messageId) {
//		String result=messageService.updateMessage(messageId);
//		return result;
//	}
//}
