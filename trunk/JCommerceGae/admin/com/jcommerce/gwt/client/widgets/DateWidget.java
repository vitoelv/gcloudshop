/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.widgets;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.util.GWTUtils;

public class DateWidget extends Composite {
    private ListBox year = new ListBox();
    private ListBox month = new ListBox();
    private ListBox day = new ListBox();
    
    private DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");

    public DateWidget() {
        Date now = new Date();
        int thisyear = now.getYear() + 1900;
        for (int y = thisyear - 10 ; y < thisyear + 10 ; y++) {
            year.addItem(""+y);
        }
        GWTUtils.setSelectedText(year, ""+thisyear);
        
        for (int m = 1 ; m <= 12 ; m++) {
            month.addItem(""+m);
        }
        month.setSelectedIndex(now.getMonth());
        
        for (int d = 1 ; d <= 31 ; d++) {
            day.addItem(""+d);
        }
        day.setSelectedIndex(now.getDate() - 1);
        
        HorizontalPanel contentPanel = new HorizontalPanel();
        
        contentPanel.add(year);
        contentPanel.add(month);
        contentPanel.add(day);

        initWidget(contentPanel);
    }
    
    public Date getValue() {
        Date d = format.parse(GWTUtils.getSelectedText(year)+"-"+GWTUtils.getSelectedText(month)+"-"+GWTUtils.getSelectedText(day));
        return d;
    }
    
    public void setValue(Date d) {
        GWTUtils.setSelectedText(year, ""+(d.getYear() + 1900));
        month.setSelectedIndex(d.getMonth());
        day.setSelectedIndex(d.getDay() - 1);
    }
}
