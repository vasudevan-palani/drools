package com.vasu.drools.stateful;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

public class MainApp {

	public static void main(String[] args) throws DroolsParserException,
	IOException {
		MainApp.stateless(args);
		MainApp.stateful(args);
	}
	
	public static void stateless(String[] args) throws DroolsParserException,
			IOException {
		MainApp ma = new MainApp();

		PackageBuilder packageBuilder = new PackageBuilder();

		InputStream resourceAsStream = ma.getClass().getResourceAsStream(
				"/alarm.drl");

		packageBuilder
				.addPackageFromDrl(new InputStreamReader(resourceAsStream));

		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		Package rulesPackage = packageBuilder.getPackage();
		ruleBase.addPackage(rulesPackage);

		StatelessSession workingMemory = ruleBase.newStatelessSession();

		String[] names = new String[] { "kitchen", "bedroom", "office",
				"livingroom" };

		Map<String, Room> name2room = new HashMap<String, Room>();
		ArrayList list  =new ArrayList();

		for (String name : names) {

			Room room = new Room(name);

			name2room.put(name, room);

			list.add(room);

			Sprinkler sprinkler = new Sprinkler(room);
			list.add(sprinkler);

		}

		workingMemory.execute(list);
		System.out.println("----------------------");
		Fire kitchenFire = new Fire(name2room.get("kitchen"));

		Fire officeFire = new Fire(name2room.get("office"));

		list.add(kitchenFire);

		list.add(officeFire);

		workingMemory.execute(list);
		System.out.println("----------------------");
	}

	public static void stateful(String[] args) throws DroolsParserException,
			IOException {
		MainApp ma = new MainApp();

		PackageBuilder packageBuilder = new PackageBuilder();

		InputStream resourceAsStream = ma.getClass().getResourceAsStream(
				"/alarm.drl");

		packageBuilder
				.addPackageFromDrl(new InputStreamReader(resourceAsStream));

		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		Package rulesPackage = packageBuilder.getPackage();
		ruleBase.addPackage(rulesPackage);

		WorkingMemory workingMemory = ruleBase.newStatefulSession();

		String[] names = new String[] { "kitchen", "bedroom", "office",
				"livingroom" };

		Map<String, Room> name2room = new HashMap<String, Room>();

		for (String name : names) {

			Room room = new Room(name);

			name2room.put(name, room);

			workingMemory.insert(room);

			Sprinkler sprinkler = new Sprinkler(room);
			workingMemory.insert(sprinkler);

		}

		workingMemory.fireAllRules();
		System.out.println("----------------------");
		Fire kitchenFire = new Fire(name2room.get("kitchen"));

		Fire officeFire = new Fire(name2room.get("office"));

		FactHandle kitchenFireHandle = workingMemory.insert(kitchenFire);

		FactHandle officeFireHandle = workingMemory.insert(officeFire);

		workingMemory.fireAllRules();
		System.out.println("----------------------");

//		workingMemory.retract(kitchenFireHandle);
//
//		workingMemory.retract(officeFireHandle);
//
//		workingMemory.fireAllRules();

	}

}
