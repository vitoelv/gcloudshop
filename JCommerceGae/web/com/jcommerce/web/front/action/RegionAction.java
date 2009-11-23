package com.jcommerce.web.front.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jcommerce.core.model.Region;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IRegion;


public class RegionAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [RegionAction]: "+s );
	}
	
    private InputStream jsonRes;
    
    
	@Override
	public String onExecute() throws Exception {
		try {
			debug("in execute");        
	        HttpServletRequest request = getRequest();
	       
	        String type = request.getParameter("type");
	        String target = request.getParameter("target");
	        String parent = request.getParameter("parent");
	        debug("type="+type+", target="+target+", parent="+parent);
	        
	        Criteria c = new Criteria();
	        c.addCondition(new Condition(IRegion.REGION_TYPE, Condition.EQUALS, type));
	        c.addCondition(new Condition(IRegion.PARENT_ID, Condition.EQUALS, parent));
	        List<Region> list = (List<Region>)getDefaultManager().getList(ModelNames.REGION, c);
	        
	        
	        JSONArray regions = new JSONArray();
	        for(Region region : list) {
	        	Map<String, String> m = new HashMap<String, String>();
	        	m.put("region_id", region.getPkId());
	        	m.put("region_name", region.getRegionName());
	        	regions.put(m);
	        }
	        
	        JSONObject res = new JSONObject();;
	        res.put("target", target);
	        res.put("type", Long.valueOf(type));
	        res.put("regions", regions);
	        
			String out = res.toString();
			debug("in [execute]: out="+out);
//			jsonRes = new StringBufferInputStream(out);
			jsonRes = new ByteArrayInputStream(out.getBytes(IWebConstants.ENC));
			
	        return SUCCESS;
	        
	        
	        
	        
	        
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}


	public InputStream getJsonRes() {
		return jsonRes;
	}


	public void setJsonRes(InputStream jsonRes) {
		this.jsonRes = jsonRes;
	}

}
