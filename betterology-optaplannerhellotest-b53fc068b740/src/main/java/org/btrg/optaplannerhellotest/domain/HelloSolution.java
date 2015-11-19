package org.btrg.optaplannerhellotest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.btrg.optaplannerhellotest.HelloApp;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;

@PlanningSolution
public class HelloSolution implements Solution<HardSoftScore>, Serializable
{
	List<HelloEntity> hellos;
	List<Integer> helloCounts;
	private final int HELLO_MAX = 25;
	private HardSoftScore score;

	public HelloSolution()
	{
		populateHellos();
		populateHelloCounts();
	}

	private void populateHellos()
	{
		if (null == hellos)
		{
			hellos = new ArrayList<HelloEntity>();
			hellos.add(new HelloEntity(17));
		}
	}

	private void populateHelloCounts()
	{
		if (null == helloCounts)
		{
			helloCounts = new ArrayList<Integer>();
			for (int i = 0; i < HELLO_MAX; i++)
			{
				helloCounts.add(new Integer(i));
			}
		}
	}

	@PlanningEntityCollectionProperty
	public List<HelloEntity> getTaskList()
	{
		if (hellos == null)
		{
			populateHellos();
		}
		return hellos;
	}

	@ValueRangeProvider(id = "helloCountRange")
	public List<Integer> getHelloCountList()
	{
		return helloCounts;
	}

	public Collection<? extends Object> getProblemFacts()
	{
		List<Object> facts = new ArrayList<Object>();
		// nothing to add because the only facts are already added automatically
		// by planner
		return facts;
	}

	public HardSoftScore getScore()
	{
		if (null == HelloApp.scoreDirector)
		{
			System.out.println("WOOPS YOU SHOULD EXPECT SOME ISSUES HERE");
		}
		HardSoftScore hardSoftScore = (HardSoftScore) HelloApp.scoreDirector
				.calculateScore();
		System.out.println("SCORE " + hardSoftScore.toString());
		return hardSoftScore;
	}

	public void setScore(HardSoftScore arg0)
	{
		this.score = score;
	}

}
