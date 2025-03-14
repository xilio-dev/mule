package com.stackoak.stackoak.application.controller.portal;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "天气")
@RestController
@RequestMapping("/weather")
public class PortalWeatherApi {
}
