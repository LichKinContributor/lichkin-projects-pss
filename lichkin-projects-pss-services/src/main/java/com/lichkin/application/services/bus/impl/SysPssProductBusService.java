package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.eq;
import com.lichkin.framework.utils.LKUrlUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssProductBusService extends LKDBService {

	public List<SysPssProductEntity> findExist(ApiKeyValues<?> params, String productCode, String productName, String barcode) {
		QuerySQL sql = new QuerySQL(false, SysPssProductEntity.class);

		addConditionId(sql, SysPssProductR.id, params.getId());
//		addConditionLocale(sql, SysPssProductR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssProductR.compId, params.getCompId(), params.getBusCompId());
//		addConditionUsingStatus(true, params.getCompId(), sql, SysPssProductR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		if (StringUtils.isBlank(barcode)) {
			if (StringUtils.isBlank(productCode)) {
				sql.eq(SysPssProductR.productName, productName);
			} else {
				sql.where(

						new Condition(true,

								new Condition(null, new eq(SysPssProductR.productCode, productCode)),

								new Condition(false, new eq(SysPssProductR.productName, productName))

						)

				);
			}
		} else {
			if (StringUtils.isBlank(productCode)) {
				sql.where(

						new Condition(true,

								new Condition(null, new eq(SysPssProductR.barcode, barcode)),

								new Condition(false, new eq(SysPssProductR.productName, productName))

						)

				);
			} else {
				sql.where(

						new Condition(true,

								new Condition(null, new eq(SysPssProductR.barcode, barcode)),

								new Condition(false, new eq(SysPssProductR.productCode, productCode)),

								new Condition(false, new eq(SysPssProductR.productName, productName))

						)

				);
			}
		}

		return dao.getList(sql, SysPssProductEntity.class);
	}


	/** 文件服务器URL根路径 */
	@Value("${com.lichkin.files.server.rootUrl}")
	private String filesServerRootUrl;

	/** 文件服务器保存根路径 */
	@Value("${com.lichkin.files.save.path:/opt/files}")
	private String filesSaveRootPath;

	/** 图片保存子路径 */
	private static final String IMAGES_PATH = "/images/pssProduct";


	public String analysisImageUrl(String url) {
		return LKUrlUtils.analysisBase64ImageUrl(true, url, filesServerRootUrl, filesSaveRootPath, IMAGES_PATH);
	}
}
