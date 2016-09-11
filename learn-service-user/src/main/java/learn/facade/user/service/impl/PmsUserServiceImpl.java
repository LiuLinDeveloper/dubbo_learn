package learn.facade.user.service.impl;

import java.util.Map;

import learn.comon.page.PageBean;
import learn.comon.page.PageParam;
import learn.facade.user.demo.entity.PmsUser;
import learn.facade.user.service.PmsUserService;
import learn.service.user.biz.PmsUserBiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("PmsUserService")
public class PmsUserServiceImpl implements PmsUserService {
	@Autowired
	private PmsUserBiz pmsUserBiz;
	public void create(PmsUser pmsUser) {
		pmsUserBiz.create(pmsUser);
	}
	
	/**
	 * 根据ID获取用户信息.
	 * @param userId
	 * @return
	 */
	public PmsUser getById(Long userId) {
		return pmsUserBiz.getById(userId);
	}

	/**
	 * 根据登录名取得用户对象
	 */
	public PmsUser findUserByUserNo(String userNo) {
		return pmsUserBiz.findUserByUserNo(userNo);
	}

	/**
	 * 根据ID删除一个用户，同时删除与该用户关联的角色关联信息. type="1"的超级管理员不能删除.
	 * 
	 * @param id
	 *            用户ID.
	 */
	public void deleteUserById(long userId) {
		PmsUser pmsUser = pmsUserBiz.getById(userId);
		if (pmsUser != null) {
			if ("1".equals(pmsUser.getUserType())) {
				throw new RuntimeException("【" + pmsUser.getUserNo() + "】为超级管理员，不能删除！");
			}
			pmsUserBiz.deleteUserById(pmsUser.getId());
		}
	}

	
	/**
	 * 更新用户信息.
	 * @param user
	 */
	public void update(PmsUser user) {
		pmsUserBiz.update(user);
	}
	
	/**
	 * 根据用户ID更新用户密码.
	 * 
	 * @param userId
	 * @param newPwd
	 *            (已进行SHA1加密)
	 */
	public void updateUserPwd(Long userId, String newPwd, boolean isTrue) {
		PmsUser pmsUser = pmsUserBiz.getById(userId);
		pmsUser.setUserPwd(newPwd);
		pmsUser.setPwdErrorCount(0); // 密码错误次数重置为0
		pmsUser.setIsChangedPwd(isTrue); // 设置密码为已修改过
		pmsUserBiz.update(pmsUser);
	}



	/**
	 * 查询并分页列出用户信息.
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return pmsUserBiz.listPage(pageParam, paramMap);
	}


}
