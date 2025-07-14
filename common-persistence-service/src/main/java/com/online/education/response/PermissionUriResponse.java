package com.online.education.response;

import com.online.education.projection.PermissionUriResponseView;
import lombok.Data;

@Data
public class PermissionUriResponse implements PermissionUriResponseView {
    private String uri;
}
