/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author leonard
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class UsageTrend implements Serializable {

    private static final long serialVersionUID = 4170601615790799177L;
    
    private String date;
    private Integer pendingCount;
    private Integer sendCount;
    private Integer smsSent;
    private Integer creditCount;

}
