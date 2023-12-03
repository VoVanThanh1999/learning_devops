package com.btc.news.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btc.news.model.News;

public interface NewsRepository extends JpaRepository<News, Integer> {
	public List<News> findByUserCreateId(Integer id);
}
