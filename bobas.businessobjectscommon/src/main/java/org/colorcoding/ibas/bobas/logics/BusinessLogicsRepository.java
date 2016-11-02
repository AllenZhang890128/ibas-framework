package org.colorcoding.ibas.bobas.logics;

import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.core.IBusinessObjectBase;
import org.colorcoding.ibas.bobas.repository.BORepositoryLogicService;

/**
 * 业务逻辑执行仓库
 * 
 * @author Niuren.Zhu
 *
 */
class BusinessLogicsRepository extends BORepositoryLogicService {

	public BusinessLogicsRepository() {
		this.setRefetchAfterSave(false);// 保存后不检索数据
		this.setUseCache(false);// 不使用缓存数据
		this.setCheckApprovalProcess(false);// 不检查审批逻辑
	}

	public IOperationResult<?> save(IBusinessObjectBase bo) {
		String token = this.getCurrentUser().getToken();
		return super.save(bo, token);
	}
}
