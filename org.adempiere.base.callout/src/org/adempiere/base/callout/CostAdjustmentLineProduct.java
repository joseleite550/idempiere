/***********************************************************************
 * This file is part of iDempiere ERP Open Source                      *
 * http://www.idempiere.org                                            *
 *                                                                     *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 *                                                                     *
 * Contributors:                                                       *
 * - hengsin                         								   *
 **********************************************************************/
package org.adempiere.base.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.annotation.Callout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTable;
import org.compiere.model.ICostInfo;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MCostElement;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MProduct;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * 
 * @author hengsin
 *
 */
@Callout(tableName = I_M_InventoryLine.Table_Name, columnName = I_M_InventoryLine.COLUMNNAME_M_Product_ID)
public class CostAdjustmentLineProduct implements IColumnCallout {
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String trxName = null;
		if (   mTab != null
			&& mTab.getTableModel() != null) {
			GridTable gt = mTab.getTableModel();
			if (gt.isImporting()) {
				trxName = gt.get_TrxName();
			}
		}
		if (mTab.getValue("M_Inventory_ID") == null)
			return null;
		MInventory inventory = new MInventory(ctx, (Integer) mTab.getValue("M_Inventory_ID"), trxName);
		if (MDocType.DOCSUBTYPEINV_CostAdjustment.equals(inventory.getC_DocType().getDocSubTypeInv())) {
			String costingMethod = inventory.getCostingMethod();
			if (value == null) {
				mTab.setValue(I_M_InventoryLine.COLUMNNAME_CurrentCostPrice, BigDecimal.ZERO);
				mTab.setValue(I_M_InventoryLine.COLUMNNAME_NewCostPrice, BigDecimal.ZERO);
			} else {
				MProduct product = MProduct.get(ctx, (Integer) value);					
				MClient client = MClient.get(ctx);
				MAcctSchema as = client.getAcctSchema();

				String costingLevel = product.getCostingLevel(as);
				if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(costingLevel)) {
					mTab.setValue(I_M_InventoryLine.COLUMNNAME_CurrentCostPrice, BigDecimal.ZERO);
					mTab.setValue(I_M_InventoryLine.COLUMNNAME_NewCostPrice, BigDecimal.ZERO);
				}else {
					Object asiValue = mTab.getValue(I_M_InventoryLine.COLUMNNAME_M_AttributeSetInstance_ID);
					int M_ASI_ID = asiValue != null ? (Integer)asiValue : 0;
					int AD_Org_ID = inventory.getAD_Org_ID();
					int C_Currency_ID = inventory.getC_Currency_ID();

					if (as.getC_Currency_ID() != C_Currency_ID) 
					{
						MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(ctx, client.get_ID());
						for (int i = 0; i < ass.length ; i ++)
						{
							MAcctSchema a =  ass[i];
							if (a.getC_Currency_ID() ==  C_Currency_ID) 
								as = a ; 
						}
					}
					ICostInfo cost = product.getCostInfo(as, AD_Org_ID, M_ASI_ID, costingMethod, inventory.getMovementDate());					
					if (cost == null) {
						if (!MCostElement.COSTINGMETHOD_StandardCosting.equals(costingMethod)) {
							mTab.setValue(mField, null);
							return Msg.getMsg(Env.getCtx(), "NoCostingRecord");
						}
					}

					mTab.setValue(I_M_InventoryLine.COLUMNNAME_CurrentCostPrice, cost.getCurrentCostPrice());
					mTab.setValue(I_M_InventoryLine.COLUMNNAME_NewCostPrice, cost.getCurrentCostPrice());	
				}
			}
		}
		return null;
	}
}