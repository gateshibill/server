package com.ylb.pc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 官网-PC端
 * @author Administrator
 *
 */
@Controller
@CrossOrigin
@RequestMapping("/pc/index")
public class PCIndexController {
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "pc/index";
	}
	/**
	 * 按天融资
	 * @return
	 */
	@RequestMapping("/antianrongzi")
	public String antianrongzi() {
		return "pc/antianpeizi";
	}
	/**
	 * 按月融资
	 * @return
	 */
	@RequestMapping("/anyuerongzi")
	public String anyuerongzi() {
		return "pc/anyuepeizi";
	}
	/**
	 * 免息融资
	 * @return
	 */
	@RequestMapping("/mianxirongzi")
	public String mianxirongzi() {
		return "pc/mianxipeizi";
	}
	/**
	 * 新手指引
	 * @return
	 */
	@RequestMapping("/xinshouzhiyin")
	public String xinshouyindao() {
		return "pc/xinshouzhiyin";
	}
	/**
	 * 我要推广
	 * @return
	 */
	@RequestMapping("/woyaotuiguang")
	public String woyaotuiguang() {
		return "pc/woyaotuiguang";
	}
	/**
	 * 软件下载
	 * @return
	 */
	@RequestMapping("/ruanjianxiazai")
	public String ruanjianxiazai() {
		return "pc/ruanjianxiazai";
	}
	/**
	 * 公司简介
	 * @return
	 */
	@RequestMapping("/gongsijianjie")
	public String gongsijianjie() {
		return "pc/gongsijianjie";
	}
	/**
	 * 安全保障
	 * @return
	 */
	@RequestMapping("/anquanbaozhang")
	public String anquanbaozhang() {
		return "pc/anquanbaozhang";
	}
	/**
	 * 办公环境
	 * @return
	 */
	@RequestMapping("/bangonghuanjing")
	public String bangonghuanjing() {
		return "pc/bangonghuanjing";
	}
	/**
	 * 注册协议
	 * @return
	 */
	@RequestMapping("/zhucexieyi")
	public String zhucexieyi() {
		return "pc/zhucexieyi";
	}
	/**
	 * 联系我们
	 * @return
	 */
	@RequestMapping("/lianxiwomen")
	public String lianxiwomen() {
		return "pc/lianxiwomen";
	}
	/**
	 * 资讯中心
	 * @return
	 */
	@RequestMapping("/zixunzhongxin")
	public String zixunzhongxin() {
		return "pc/zixunzhongxin";
	}
	/**
	 * 资讯详情
	 * @return
	 */
	@RequestMapping("/zixunxiangqing")
	public String zixunxiangqing() {
		return "pc/zixunxiangqing";
	}
	/**
	 * 网站公告
	 * @return
	 */
	@RequestMapping("/wanzhangonggao")
	public String wanzhangonggao() {
		return "pc/wangzhangonggao";
	}
	/**
	 * 测试长连接1
	 * @return
	 */
	@RequestMapping("/testsocket")
	public String testsocket() {
		return "pc/test";
	}
	/**
	 * 测试长连接2
	 * @return
	 */
	@RequestMapping("/testsocket2")
	public String testsocket2() {
		return "pc/test2";
	}
}
