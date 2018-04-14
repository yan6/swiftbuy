package com.ywj.swiftbuy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ywj.swiftbuy.admin.BusinessBean;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessListItem {
    private List<BusinessBean> businessList;
    private String nextPageUrl;
}
