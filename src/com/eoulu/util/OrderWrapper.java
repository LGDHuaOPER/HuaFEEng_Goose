package com.eoulu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author Administrator
 * 1. 使用正则表达式来判断数字，多个连续的数字作为一组,

2.  一次检索出数字组合，

3. 检出下一组数字,如果有,则进入步骤4，否则进入步骤6.

4. 如果两组数字出现的位置相等，并且前面部分的字符串相等，则进入第5步。否则break，跳到第6步.

5. 如果前面部分的字符串完全一致。则比较两个数字的大小，如果大小一致，则进入下一组，即步骤3.如果大小不一致，则可以比对出来大小，比较结束

6. 调用String的compareTo方法,并返回(流程结束)
 *
 */
public class OrderWrapper implements Comparable<OrderWrapper>{  
    String name = null;  
    public OrderWrapper(String name){  
        this.name = name;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
      
    @Override  
    public String toString() {  
        return String.valueOf(name);  
    }  
    @Override  
    public boolean equals(Object obj) {  
        if(obj == this){  
            return true;  
        }   
          
        if(obj instanceof OrderWrapper){  
            OrderWrapper other = (OrderWrapper)obj;  
      
            if(null == this.name){  
                return false;  
            } else {  
                return this.name.equals(other.name);  
            }  
        }  
        return false;  
    }  
  
    // 比较方法,相当于减法。 (return this - wrapper)  
    public int compareTo(OrderWrapper wrapper) {  
        if(null == wrapper){  
            return 1;  
        }  
        // 直接相等  
        if(this == wrapper || this.equals(wrapper)){  
            return 0;  
        }  
        String name1 = this.name;  
        String name2 = wrapper.name;  
        // 特殊情形，name有一个为空的情况.  
        if(null == name1){  
            // 都为空，认为相对  
            if(null == name2){  
                return 0;  
            } else {  
                return -1;  
            }  
        } else if(null == name2){  
            return 1;  
        }  
        // 中间 1-多个数字  
        Pattern pattern = Pattern.compile("\\D*(\\d+)\\D*");  
        Matcher matcher1 = pattern.matcher(name1);  
        Matcher matcher2 = pattern.matcher(name2);  
        //System.out.println(pattern.pattern());  
        //  
        int index1_step = 0;  
        int index2_step = 0;  
        while(matcher1.find() && matcher2.find()){  
            String s1 = matcher1.group(1);  
            String s2 = matcher2.group(1);  
            int index1 = name1.indexOf(s1, index1_step);  
  
            int index2 = name2.indexOf(s2, index2_step);  
            
         
       
            //  
            index1_step = index1;  
            index2_step = index2;  
            // 索引相等的情况下  
            if(index1 == index2){   
                String pre1 = name1.substring(0, index1);  
                String pre2 = name2.substring(0, index2);  
                if(pre1.equals(pre2)){  
                    //   
                    long num1 = Long.parseLong(s1);  
                    long num2 = Long.parseLong(s2);  
                    //  
                    if(num1 == num2){  
                        // 比较下一组  
                        continue;  
                    } else {  
                        return (int)(num1 - num2);  
                    }  
                } else {  
                    break;  
                }  
            } else {  
                break;  
            }  
        }  
          
        // 最后的情形.  
        return this.name.compareTo(wrapper.name);  
    }  
      
}
