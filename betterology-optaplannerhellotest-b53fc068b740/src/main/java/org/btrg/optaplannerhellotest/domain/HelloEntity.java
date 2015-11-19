package org.btrg.optaplannerhellotest.domain;

import org.btrg.optaplannerhellotest.HelloApp;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class HelloEntity {
	private int count;

	private HelloEntity() {
	}

	public HelloEntity(int i) {
		this.count = i;
	}

	@PlanningVariable(valueRangeProviderRefs = { "helloCountRange" })
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		System.out.print(HelloApp.getSpacerFeed() + count);
		this.count = count;
	}

}
