package com.ylb.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ylb.entity.Bulletin;
import com.ylb.entity.Group;
import com.ylb.entity.Message;
import com.ylb.entity.OfficialAccount;
import com.ylb.entity.Ofmucaffiliation;
import com.ylb.entity.User;
import com.ylb.service.BulletinService;
import com.ylb.service.GroupService;
import com.ylb.service.MessageService;
import com.ylb.service.OfPropertyService;
import com.ylb.service.UserService;
import com.ylb.util.BaseUtil;
import com.ylb.util.DateUtil;
import com.ylb.util.JsonUtil;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseUtil {
	private final static Logger logger = LoggerFactory.getLogger(GroupController.class);
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userOfService;
	@Autowired
	private OfPropertyService ofPropertyService;
	@Autowired
	private BulletinService bulltinService;
	@Autowired
	private MessageService messageService;

	/**
	 * 用户列表
	 * 
	 * @return
	 */

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "group/index";
	}

	/**
	 * 增加群公告
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/addGroupBulletin", method = RequestMethod.POST)
	public void addGroupBulletin(HttpServletRequest request, HttpServletResponse response) {
		logger.info("addGroupBulletin()");
		try {
			InputStream is = request.getInputStream();
			String message = IOUtils.toString(is, "UTF-8");
			logger.info("message:" + message);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
			Bulletin bulletin = gson.fromJson(message, Bulletin.class);

			bulltinService.addBulletin(bulletin);
			output(response, JsonUtil.buildSuccessJson("0", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 获取群公告
	 * 
	 * @param request
	 * @param response
	 * @param groupId
	 */
	@ResponseBody
	@RequestMapping(value = "/getGroupBulletin", method = RequestMethod.GET)
	public void getGroupBulletin(HttpServletRequest request, HttpServletResponse response, String groupId) {
		try {
			logger.info("getGroupBulletin():" + groupId);
			Bulletin bulltin = bulltinService.getLatestBulletinByGroupId(groupId, 0, 10);
			if (null != bulltin) {
				output(response, JsonUtil.objectToJson("0", bulltin));
			} else {
				output(response, JsonUtil.buildObjectJson("2", "没有群公告"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getGroupByName", method = RequestMethod.GET)
	public void getGroupByName(HttpServletRequest request, HttpServletResponse response, String name) {
		logger.info("getGroupByName() " + name);
		if (null == name) {
			output(response, JsonUtil.buildObjectJson("2", "name不能为空"));
			return;
		}
		try {
			Group group = groupService.getGroupByName(name);
			output(response, JsonUtil.buildObjectJson(0, "success", group));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyAdminGroupNickname", method = RequestMethod.GET)
	public void modifyAdminGroupNickname(HttpServletRequest request, HttpServletResponse response, String jid,
			String groupName, String userGroupNickname) {
		logger.info("modifyAdminGroupNickname() " + jid + "/" + groupName + "/" + userGroupNickname);
		if (null == jid || null == groupName || null == userGroupNickname) {
			output(response, JsonUtil.buildObjectJson("2", "jid|groupName|userGroupNickname不能为空"));
			return;
		}
		try {
			groupName= groupName.split("@")[0];
			Ofmucaffiliation ofmucaffiliation = groupService.modifyAdminGroupNickname(jid, groupName,
					userGroupNickname);
			output(response, JsonUtil.buildObjectJson(0, "success", ofmucaffiliation));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getGroupDetailByName", method = RequestMethod.GET)
	public void getGroupDetailByName(HttpServletRequest request, HttpServletResponse response, String name) {
		logger.info("getGroupByName() " + name);
		if (null == name) {
			output(response, JsonUtil.buildObjectJson("2", "name不能为空"));
			return;
		}
		try {
			Group group = groupService.getGroupDetailByName(name);
			output(response, JsonUtil.buildObjectJson(0, "success", group));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getGroupListByJid", method = RequestMethod.GET)
	public void getGroupMembers(HttpServletRequest request, HttpServletResponse response, String jid, Integer page,
			Integer limit) {
		logger.info("getGroupListByJid() " + jid);
		if (null == jid) {
			output(response, JsonUtil.buildObjectJson("2", "name不能为空"));
			return;
		}
		try {
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("jid", jid);
			param.put("page", page);
			param.put("limit", limit);

			PageHelper.startPage(page, limit);
			Page<Group> data = groupService.getGroupListByJid(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<Group> list = data.getResult();
			logger.info("getGroupListByJid():" + list.size());
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
			// output(response, JsonUtil.buildObjectJson(0, "success", group));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 获取群管理员
	 * 
	 * @param request
	 * @param response
	 * @param groupId
	 * @param page
	 * @param limit
	 */
	@ResponseBody
	@RequestMapping(value = "/getGroupAdminListByGroupId", method = RequestMethod.GET)
	public void getGroupAdminListByGroupId(HttpServletRequest request, HttpServletResponse response, String groupId,
			String startTime, String endTime, Integer page, Integer limit) {
		logger.info("getGroupAdminListByGroupId()");
		try {
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Long sdate = (null == startTime || startTime.isEmpty()) ? null : DateUtil.getStartTime(startTime);
			Long edate = (null == endTime || endTime.isEmpty()) ? null : DateUtil.getEndTime(endTime);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("groupId", groupId);
			param.put("sdate", sdate);
			param.put("edate", edate);
			PageHelper.startPage(page, limit);
			Page<User> data = groupService.getGroupAdminListByGroupId(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<User> list = data.getResult();
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 获取群所有者，群主
	 * 
	 * @param request
	 * @param response
	 * @param groupId
	 * @param page
	 * @param limit
	 */
	@ResponseBody
	@RequestMapping(value = "/getGroupOwnerListByGroupId", method = RequestMethod.GET)
	public void getGroupOwnerListByGroupId(HttpServletRequest request, HttpServletResponse response, String groupId,
			String startTime, String endTime, Integer page, Integer limit) {
		logger.info("getGroupAdminListByGroupId() groupId=" + groupId);
		try {
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Long sdate = (null == startTime || startTime.isEmpty()) ? null : DateUtil.getStartTime(startTime);
			Long edate = (null == endTime || endTime.isEmpty()) ? null : DateUtil.getEndTime(endTime);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("groupId", groupId);
			param.put("sdate", sdate);
			param.put("edate", edate);
			PageHelper.startPage(page, limit);
			Page<User> data = groupService.getGroupOwnerListByGroupId(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<User> list = data.getResult();
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 获取群成员
	 * 
	 * @param request
	 * @param response
	 * @param groupId
	 * @param page
	 * @param limit
	 */
	@ResponseBody
	@RequestMapping(value = "/getGroupMemberListByGroupId", method = RequestMethod.GET)
	public void getGroupMemberListByGroupId(HttpServletRequest request, HttpServletResponse response, String groupId,
			String startTime, String endTime, Integer page, Integer limit) {
		logger.info("getGroupMemberListByGroupId():" + groupId);
		try {
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Long sdate = (null == startTime || startTime.isEmpty()) ? null : DateUtil.getStartTime(startTime);
			Long edate = (null == endTime || endTime.isEmpty()) ? null : DateUtil.getEndTime(endTime);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("groupId", groupId);
			param.put("sdate", sdate);
			param.put("edate", edate);
			PageHelper.startPage(page, limit);
			Page<User> data = groupService.getGroupMemberListByGroupId(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<User> list = data.getResult();
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 根据发送者获取群消息
	 * 
	 * @param request
	 * @param response
	 * @param sender
	 * @param page
	 * @param limit
	 */
	@ResponseBody
	@RequestMapping(value = "/getGroupMessageListBySender", method = RequestMethod.GET)
	public void getGroupMessageListBySender(HttpServletRequest request, HttpServletResponse response, String sender,
			Integer page, Integer limit) {
		logger.info("getGroupMessageListBySender()");
		page = (null == page || page < 0) ? 0 : page;
		limit = (null == limit || limit < 0) ? 10 : limit;
		try {
			List<Message> list = groupService.getGroupMessageListBySender(sender, page, limit);
			output(response, JsonUtil.buildObjectJson(0, "success", list));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 搜索用户创建群的消息
	 * 
	 * @param request
	 * @param response
	 * @param userId，
	 * @param sender,发送
	 * @param name,利宝号
	 * @param username,用户名
	 * @param groupId，
	 * @param messageId
	 * @param content
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param limit
	 */
	@ResponseBody
	@RequestMapping(value = "/searchMessageList", method = RequestMethod.GET)
	public void searchMessageList(HttpServletRequest request, HttpServletResponse response, Integer messageId,
			String username, String nickname, String name, String groupId, String content, String startTime,
			String endTime, Integer page, Integer limit) {
		logger.info("searchMessageList()");
		try {
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Long sdate = (null == startTime || startTime.isEmpty()) ? null : DateUtil.getStartTime(startTime);
			Long edate = (null == endTime || endTime.isEmpty()) ? null : DateUtil.getEndTime(endTime);
			if (null == groupId) {
				output(response, JsonUtil.buildObjectJson("2", "groupId 不能为空"));
				return;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			if (!groupId.contains("@")) {
				String domain = ofPropertyService.getOfProperty("xmpp.domain").getPropValue();
				groupId = groupId + "@conference." + domain;
			}
			param.put("messageId", messageId);
			param.put("username", username);
			param.put("nickname", nickname);
			param.put("name", name);
			param.put("groupId", groupId);
			param.put("content", content);
			param.put("content", content);
			param.put("sdate", sdate);
			param.put("edate", edate);
			param.put("page", page);
			param.put("limit", limit);
			PageHelper.startPage(page, limit);
			Page<Message> data = messageService.searchMessageList(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<Message> list = data.getResult();
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 搜索用户创建的群
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @param rooID
	 * @param name
	 * @param page
	 * @param limit
	 */
	@ResponseBody
	@RequestMapping(value = "/searchUserGroup", method = RequestMethod.GET)
	public void searchUserGroup(HttpServletRequest request, HttpServletResponse response, String username,
			String roomID, String name, String startTime, String endTime, Integer page, Integer limit) {
		logger.info(" info searchUserGroup()");
		try {
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;

			Long sdate = (null == startTime || startTime.isEmpty()) ? null : DateUtil.getStartTime(startTime);
			Long edate = (null == endTime || endTime.isEmpty()) ? null : DateUtil.getEndTime(endTime);

			Map<String, Object> param = new HashMap<String, Object>();

			if (null == username) {
				output(response, JsonUtil.buildObjectJson("2", "username不能为空"));
			}
			if (!username.contains("@")) {
				String domain = ofPropertyService.getOfProperty("xmpp.domain").getPropValue();
				username = username + "@" + domain;
			}
			logger.info("searchUserGroup() userId:" + username);
			param.put("userId", username);
			param.put("roomID", roomID);
			param.put("name", name);
			param.put("sdate", sdate);
			param.put("edate", edate);
			PageHelper.startPage(page, limit);
			Page<Group> data = groupService.searchUserGroup(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<Group> list = data.getResult();
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/searchPublicGroup", method = RequestMethod.GET)
	public void searchPublicGroup(HttpServletRequest request, HttpServletResponse response, String subject,
			Integer page, Integer limit) {
		logger.info("searchPublicGroup() " + subject);
		if (null == subject) {
			output(response, JsonUtil.buildObjectJson("2", "subject不能为空"));
			return;
		}
		try {
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Map<String, Object> param = new HashMap<String, Object>();
			logger.info("searchUserGroup() subject:" + subject);
			param.put("subject", subject);
			PageHelper.startPage(page, limit);
			Page<Group> data = groupService.searchUserGroup(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<Group> list = data.getResult();
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}
}
