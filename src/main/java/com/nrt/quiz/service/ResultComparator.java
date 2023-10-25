package com.nrt.quiz.service;

import java.util.Comparator;

import com.nrt.quiz.entity.UserPlayedQuizHistory;

public class ResultComparator implements Comparator<UserPlayedQuizHistory> {
 

	@Override
	public int compare(UserPlayedQuizHistory o1, UserPlayedQuizHistory o2) {
		   return Integer.compare(o1.getScore(), o2.getScore());
	}
}