package com.kedian.repository;

import com.kedian.domain.DownloadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.repository
 * @date 2018/6/11 11:22
 * @Description:
 */

//@Repository
public interface DownloadInfoRepository extends JpaRepository<DownloadInfo, String> {
    DownloadInfo findByBaseUrl(String baseUrl);
}
