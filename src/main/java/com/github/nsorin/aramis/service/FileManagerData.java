package com.github.nsorin.aramis.service;

import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;

public record FileManagerData(TextContent textContent,
                              FileProperties fileProperties) {
}
