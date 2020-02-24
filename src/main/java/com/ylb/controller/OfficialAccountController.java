package com.ylb.controller;

import java.io.InputStream;
import java.util.Date;
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
import com.ylb.entity.Group;
import com.ylb.entity.OfficialAccount;
import com.ylb.entity.Topic;
import com.ylb.entity.User;
import com.ylb.entity.UserManageOaRole;
import com.ylb.service.GroupService;
import com.ylb.service.OfPropertyService;
import com.ylb.service.OfficialAccountService;
import com.ylb.service.TopicService;
import com.ylb.service.UserManageOaRoleService;
import com.ylb.service.UserService;
import com.ylb.util.BaseUtil;
import com.ylb.util.DateUtil;
import com.ylb.util.HttpUtils;
import com.ylb.util.JsonUtil;

@Controller
@RequestMapping("/officialAccount")
public class OfficialAccountController extends BaseUtil {
	private final static Logger logger = LoggerFactory.getLogger(OfficialAccountController.class);

	@Autowired
	private OfficialAccountService officialAccountService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private UserService userOfService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private OfPropertyService ofPropertyService;
	@Autowired
	private UserManageOaRoleService userManageOaRoleService;

	/**
	 * 添加公众号列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addOfficialAccout", method = RequestMethod.POST)
	public void addOfficialAccout(HttpServletRequest request, HttpServletResponse response,
			OfficialAccount officialAccount) {
		logger.info("getOfficialAccountById");
		officialAccount.setCreateTime(new Date());
		officialAccountService.addOfficialAccount(officialAccount);
		output(response, JsonUtil.buildSuccessJson("0", "success"));
	}

	/**
	 * 获取公众号详情
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOfficialAccountById", method = RequestMethod.GET)
	public void getOfficialAccountById(HttpServletRequest request, HttpServletResponse response, Integer id) {
		logger.info("getOfficialAccountById");
		OfficialAccount oa = officialAccountService.getOfficialAccountById(id);
		output(response, JsonUtil.objectToJson("0", oa));
	}

	/**
	 * 获取用户关联公众号
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserOfficialAccountListByUserId", method = RequestMethod.GET)
	public void getUserOfficialAccountListByUserId(HttpServletRequest request, HttpServletResponse response,
			String username) {
		logger.info("getUserOfficialAccountListByUserId() "+username);
		try {
			List<OfficialAccount> list = officialAccountService.getUserOfficialAccountListByUserId(username);
			output(response, JsonUtil.buildCustomJson("0", "success", list));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 获取用户关注公众号
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFollowOfficialAccountListByUserId", method = RequestMethod.GET)
	public void getFollowOfficialAccountListByUserId(HttpServletRequest request, HttpServletResponse response,
			String username, Integer page, Integer limit) {
		logger.info("getFollowOfficialAccountListByUserId() " + username);
		page = (null == page || page < 0) ? 0 : page;
		limit = (null == limit || limit < 0) ? 10 : limit;
		try {
			List<OfficialAccount> list = officialAccountService.getFollowOfficialAccountListByUserId(username, page,
					limit);
			output(response, JsonUtil.buildCustomJson("0", "success", list));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 获取公众号粉丝
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFollowerListByOfficialAccountId", method = RequestMethod.GET)
	public void getFollowerListByOfficialAccountId(HttpServletRequest request, HttpServletResponse response,
			String officialAccountId, Integer page, Integer limit) {
		logger.info("getFollowerListByOfficialAccountId");
		page = (null == page || page < 0) ? 0 : page;
		limit = (null == limit || limit < 0) ? 10 : limit;
		try {
			List<User> list = officialAccountService.getFollowerListByOfficialAccountId(officialAccountId, page * limit,
					limit);
			output(response, JsonUtil.buildCustomJson("0", "success", list));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 搜索公众号粉丝, 只能搜索自己公众号的粉丝
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchOfficialAccountFollower", method = RequestMethod.GET)
	public void searchOfficialAccountFollower(HttpServletRequest request, HttpServletResponse response,
			String officialAccountId, String username, String name, String phone, String startTime, String endTime,
			Integer page, Integer limit) {
		logger.info("searchFollowerListByOfficialAccountId() ");

		try {
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Long sdate = (null == startTime||startTime.isEmpty()) ? null : DateUtil.getStartTime(startTime);
			Long edate = (null == endTime||endTime.isEmpty()) ? null : DateUtil.getEndTime(endTime);
			if (null == officialAccountId) {
				output(response, JsonUtil.buildObjectJson("2", "公众号ID不能为空"));
				return;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("officialAccountId", officialAccountId);
			param.put("username", username);
			param.put("name", name);
			param.put("sdate", sdate);
			param.put("edate", edate);
			param.put("page", page);
			param.put("limit", limit);
			PageHelper.startPage(page, limit);
			Page<User> data = (Page<User>) userOfService.searchOfficialAccountFollower(param);
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
	 * 获取用户关注公众号的文章
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFollowOfficialAccountTopicByUserId", method = RequestMethod.GET)
	public void getFollowOfficialAccountTopicByUserId(HttpServletRequest request, HttpServletResponse response,
			String username, Integer page, Integer limit) {
		logger.info("getFollowOfficialAccountTopicByUserId() " + username);
		page = (null == page || page < 0) ? 0 : page;
		limit = (null == limit || limit < 0) ? 10 : limit;
		try {
			List<Topic> list = topicService.getFollowOfficialAccountTopicListByUserId(username, page * limit, limit);
			if (null == list) {
				output(response, JsonUtil.buildObjectJson("1", "没有找到信息"));
				return;
			}
			output(response, JsonUtil.buildCustomJson("0", "success", list));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 获取文章详情
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOfficialAccountTopicByTopicId", method = RequestMethod.GET)
	public void getOfficialAccountTopicByTopicId(HttpServletRequest request, HttpServletResponse response,
			Integer topicId) {
		logger.info("getOfficialAccountTopicByTopicId() " + topicId);
		try {
			Topic topic = topicService.getTopicById(topicId);
			if (null == topic) {
				output(response, JsonUtil.buildObjectJson("2", "没有找到信息"));
				return;
			}
			output(response, JsonUtil.buildObjectJson(0, "success", topic));
		} catch (Exception e) {
			e.getStackTrace();
			logger.info(e.getMessage());
			logger.info(e.toString());
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 获取所有公众号列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/geOfficialAccountList", method = RequestMethod.GET)
	public void geOfficialAccountList(HttpServletRequest request, HttpServletResponse response, Integer page,
			Integer limit) {
		logger.info("geOfficialAccountList");
		page = (null == page || page < 0) ? 0 : page;
		limit = (null == limit || limit < 0) ? 10 : limit;
		List<OfficialAccount> list = officialAccountService.getAllOfficialAccountList(page * limit, limit);

		output(response, JsonUtil.buildCustomJson("0", "success", list));
	}

	/**
	 * 获取用户管理的公众号列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getManageUserOfficialAccountList", method = RequestMethod.GET)
	public void getManageUserOfficialAccountList(HttpServletRequest request, HttpServletResponse response,
			String username, Integer page, Integer limit) {
		try {
			logger.info("getManageUserOfficialAccountList()");

			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("username", username);
			param.put("page", page);
			param.put("limit", limit);

			PageHelper.startPage(page, limit);
			Page<OfficialAccount> data = officialAccountService.getManageUserOfficialAccountList(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<OfficialAccount> list = data.getResult();
			logger.info("getManageUserOfficialAccountList():" + list.size());
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 关注公众号
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/followOfficialAccount", method = RequestMethod.GET)
	public void followOfficialAccount(HttpServletRequest request, HttpServletResponse response,
			Integer officialAccountId, String username) {
		logger.info("qqqfollowOfficialAccount():" + officialAccountId + "/" + username);
		officialAccountService.followOfficialAccount(officialAccountId, username, 1);
		try {
			OfficialAccount officialAccount = officialAccountService.getOfficialAccountById(officialAccountId);
			if (null == officialAccount) {
				output(response, JsonUtil.buildSuccessJson("2", "公众号不存在"));
				return;
			} else {
				String subscribeServiceId = officialAccount.getSubscribeServiceId();
				if (null == subscribeServiceId) {
					output(response, JsonUtil.buildSuccessJson("3", "获取公众号服务ID失败"));
					return;
				} else {
					int code = HttpUtils.subscribeOfficailAccoutServer(request, subscribeServiceId, username);
					if (200 == code) {
						output(response, JsonUtil.buildSuccessJson("0", "success"));
					} else {
						output(response, JsonUtil.buildSuccessJson("4", "取消关注公众号失败"));
					}
				}
			}
		} catch (Exception e) {
			logger.info("fail to followOfficialAccount:" + e.toString());
			output(response, JsonUtil.buildSuccessJson("1", "系统异常"));
		}
	}

	/**
	 * 取消关注公众号
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/unfollowOfficialAccount", method = RequestMethod.GET)
	public void unfollowOfficialAccount(HttpServletRequest request, HttpServletResponse response,
			Integer officialAccountId, String username) {
		logger.info("unfollowOfficialAccount");
		officialAccountService.followOfficialAccount(officialAccountId, username, 0);
		try {
			OfficialAccount officialAccount = officialAccountService.getOfficialAccountById(officialAccountId);
			if (null == officialAccount) {
				output(response, JsonUtil.buildSuccessJson("2", "公众号不存在"));
				return;
			} else {
				String subscribeServiceId = officialAccount.getSubscribeServiceId();
				if (null == subscribeServiceId) {
					output(response, JsonUtil.buildSuccessJson("3", "获取公众号服务ID失败"));
					return;
				} else {
					int code = HttpUtils.unsubscribeOfficailAccoutServer(request, subscribeServiceId, username);
					if (200 == code) {
						output(response, JsonUtil.buildSuccessJson("0", "success"));
					} else {
						output(response, JsonUtil.buildSuccessJson("4", "取消关注公众号失败"));
					}
				}
			}
		} catch (Exception e) {
			logger.error("fail to unfollowOfficialAccount");
			output(response, JsonUtil.buildSuccessJson("1", "系统异常"));
		}
	}

	/**
	 * 申请公众号
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/applyOfficialAccount", method = RequestMethod.POST)
	public void applyOfficialAccount(HttpServletRequest request, HttpServletResponse response) {
		logger.info("applyOfficialAccount()");
		try {
			InputStream is = request.getInputStream();
			String message = IOUtils.toString(is, "UTF-8");
			logger.info("message:" + message);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
			OfficialAccount officialAccount = gson.fromJson(message, OfficialAccount.class);
			logger.info("OfficialAccount:" + officialAccount.getUsername() + "/" + officialAccount.getName());

			List<OfficialAccount> list = officialAccountService
					.getUserOfficialAccountListByUserId(officialAccount.getUsername());
			if (list.size() > 0) {
				output(response, JsonUtil.buildObjectJson("2", "已经申请过公众号"));
				return;
			}
			officialAccount.setCreateTime(new Date());
			int officialAccountId = officialAccountService.addOfficialAccount(officialAccount);
			officialAccount.setId(officialAccountId);

			//增加创建者管理
			UserManageOaRole userManageOaRole = new UserManageOaRole();
			userManageOaRole.setOfficialAccountId(officialAccountId);
			userManageOaRole.setRoleId(1);//创建者
			userManageOaRole.setUsername(officialAccount.getUsername());
			userManageOaRole.setStatus(0);
			userManageOaRole.setCreateTime(new Date());
			userManageOaRole.setUpdateTime(new Date());
			userManageOaRoleService.addManageUser(userManageOaRole);
			
			output(response, JsonUtil.buildObjectJson(0, "success", officialAccount));
			// output(response, JsonUtil.buildCustomJson("0", "success", new ArrayList()));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 编辑公众号
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editOfficialAccount", method = RequestMethod.POST)
	public void editOfficialAccount(HttpServletRequest request, HttpServletResponse response) {
		logger.info("editOfficialAccount");
		try {
			InputStream is = request.getInputStream();
			String message = IOUtils.toString(is, "UTF-8");
			logger.info("message:" + message);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
			OfficialAccount officialAccount = gson.fromJson(message, OfficialAccount.class);
			logger.info("OfficialAccount:" + officialAccount.getId());
			officialAccountService.updateOfficialAccount(officialAccount);
			output(response, JsonUtil.buildSuccessJson("0", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 搜索公众号
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchOfficialAccount", method = RequestMethod.GET)
	public void searchOfficialAccount(HttpServletRequest request, HttpServletResponse response, String keyword,
			Integer page, Integer limit) {
		try {
			logger.info("searchOfficialAccount");
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			List<OfficialAccount> list = officialAccountService.searchOfficialAccountListByKeyword(keyword,
					page * limit, limit);
			output(response, JsonUtil.buildCustomJson("0", "success", list));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 添加公众号文章
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addOfficialAccountTopic", method = RequestMethod.POST)
	public void addOfficialAccountTopic(HttpServletRequest request, HttpServletResponse response) {
		try {
			InputStream is = request.getInputStream();
			String message = IOUtils.toString(is, "UTF-8");
			logger.info("message:" + message);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
			Topic topic = gson.fromJson(message, Topic.class);
			logger.info("addOfficialAccountTopic() " + topic.getOfficialAccountId() + "/" + topic.getTitle());
			topicService.addTopic(topic);
			Integer id = topic.getId();

			output(response, JsonUtil.buildObjectJson(0, "success", id));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 推送公众号信息
	 */
	@RequestMapping(value = "/pushOfficailAccountMessage", method = RequestMethod.GET)
	@ResponseBody
	public void publishOfficailAccountMessage(HttpServletRequest request, HttpServletResponse response,
			Integer officialAccountId, String username, Integer id, Integer type, String content) {
		logger.info("publishOfficailAccountMessage():" + officialAccountId + "/" + username + "/" + id + "/"
				+ type + "/" + content);

		if (null == officialAccountId) {
			output(response, JsonUtil.buildObjectJson("6", "officialAccountId不能为空"));
			return;
		}
		if (null == username) {
			output(response, JsonUtil.buildObjectJson("7", "username不能为空"));
			return;
		}
		if (null == type) {
			output(response, JsonUtil.buildObjectJson("8", "type不能为空"));
			return;
		}
		if (null == content) {
			output(response, JsonUtil.buildObjectJson("9", "content不能为空"));
			return;
		}
		try {
			OfficialAccount officialAccount = officialAccountService.getOfficialAccountById(officialAccountId);
			if (null == officialAccount) {
				output(response, JsonUtil.buildSuccessJson("4", "公众号不存在"));
				return;
			} else {
				String subscribeServiceId = officialAccount.getSubscribeServiceId();
				if (null == subscribeServiceId) {
					output(response, JsonUtil.buildSuccessJson("5", "获取公众号服务ID失败"));
					return;
				} else {
					Topic topic = new Topic();
					// 群处理逻辑
					if (2 == type) {
						Group group = groupService.getGroupByName(content);
						if (null == group) {
							output(response, JsonUtil.buildObjectJson("2", "群名称错误"));
							return;
						}
						String domain = ofPropertyService.getOfProperty("xmpp.domain").getPropValue();
						String groupId = group.getName() + "@conference." + domain;
						content = String.format("{\"jid\":\"%s\",\"subject\":\"%s\",\"naturalName\":\"%s\",\"membersOnly\":%d}", groupId,
								group.getSubject(), group.getNaturalName(),group.getMembersOnly(),officialAccountId);
						topic.setType(type);
						topic.setOfficialAccountId(officialAccountId);
						topic.setContent(content);
						topic.setCreateTime(new Date());
						topicService.addTopic(topic);
						id=topic.getId();
					} else {
						topic.setId(id);
					}
					int code = HttpUtils.pushOfficailAccoutMessage(request, subscribeServiceId, username, id, type,
							id.toString());
					if (200 == code) {
						topic.setStatus(1);
						topicService.updateTopic(topic);
						output(response, JsonUtil.buildObjectJson("0", "success"));
					} else {
						topic.setStatus(2);
						topicService.updateTopic(topic);
						output(response, JsonUtil.buildObjectJson("3", "系统异常"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 搜索公众号文章
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchOfficialAccountTopic", method = RequestMethod.GET)
	public void searchOfficialAccountTopic(HttpServletRequest request, HttpServletResponse response,
			String officialAccountId,Integer id,Integer type, String title, String content, String url, String startTime, String endTime,
			Integer page, Integer limit) {
		logger.info("searchOfficialAccountTopic() ");

		try {
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Long sdate = (null == startTime||startTime.isEmpty()) ? null : DateUtil.getStartTime(startTime);
			Long edate = (null == endTime||endTime.isEmpty()) ? null : DateUtil.getEndTime(endTime);
			if (null == officialAccountId) {
				output(response, JsonUtil.buildObjectJson("2", "公众号ID不能为空"));
				return;
			}
			Map<String, Object> param = new HashMap<String, Object>();

			param.put("officialAccountId", officialAccountId);
			param.put("id", id);
			param.put("type", type);
			param.put("title", title);
			param.put("url", url);
			param.put("content", content);
			param.put("sdate", sdate);
			param.put("edate", edate);
			param.put("page", page);
			param.put("limit", limit);
			PageHelper.startPage(page, limit);
			Page<Topic> data = topicService.searchOfficialAccountTopic(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<Topic> list = data.getResult();
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}
}
