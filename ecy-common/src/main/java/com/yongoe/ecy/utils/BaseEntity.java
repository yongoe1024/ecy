package com.yongoe.ecy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity基类
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private Long current;

    private Long size;

}
