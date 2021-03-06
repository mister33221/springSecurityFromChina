package com.springSecurityExample.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//自定義登入邏輯
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//1.根據用戶名去數據庫查詢，如果不存在就拋異常
		if (!"admin".equals(username)) {
			throw new UsernameNotFoundException("用戶名不存在");
		}
		//2.比較密碼(存入資料庫的通常是已經加密的 所以比較加密級的就可以了)，如果匹配成功返回UserDetails
		String password = passwordEncoder.encode("123");
		return new User(username, password, 
				//在P017會用到賦予的這些權限 嚴格區分大小寫
//				AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal"));
				//P018 除了權限外 還可以賦予使用者角色 而角色有命名規定，必須為"ROLE_XXX"，XXX為自定義，
				//前面則必須為ROLE_開頭
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_role1,/,main.html"));

	}

}
