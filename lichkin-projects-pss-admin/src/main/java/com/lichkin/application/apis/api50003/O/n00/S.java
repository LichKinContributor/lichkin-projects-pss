package com.lichkin.application.apis.api50003.O.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.services.LKApiBusGetOneService;

@Service("SysPssProductO00Service")
public class S extends LKApiBusGetOneService<I, O, SysPssProductEntity> {

	/** 文件服务器URL根路径 */
	@Value("${com.lichkin.files.server.rootUrl}")
	private String fileServerRootUrl;


	@Override
	protected void setOtherValues(SysPssProductEntity entity, String id, I sin, String locale, String compId, String loginId, O out) {
		if (StringUtils.isNotBlank(out.getImageUrl1())) {
			out.setImageUrl1(fileServerRootUrl + out.getImageUrl1());
		}
		if (StringUtils.isNotBlank(out.getImageUrl2())) {
			out.setImageUrl2(fileServerRootUrl + out.getImageUrl2());
		}
		if (StringUtils.isNotBlank(out.getImageUrl3())) {
			out.setImageUrl3(fileServerRootUrl + out.getImageUrl3());
		}
		if (StringUtils.isNotBlank(out.getImageUrl4())) {
			out.setImageUrl4(fileServerRootUrl + out.getImageUrl4());
		}
	}

}
