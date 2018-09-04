/**
 * 
 */
package com.eoulu.service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangkai
 *
 */
public interface EquipmentService {

   public List<Map<String,Object>> getAllEquipmentByName(String model);
   
   public List<Map<String,Object>> getAllEquipmentInfo();
   
   public List<Map<String,Object>> getAllEquipmentByOrderID(String OrderID);
}
