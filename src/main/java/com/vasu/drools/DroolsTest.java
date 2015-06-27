package com.vasu.drools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

public class DroolsTest {

	public DroolsTest() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws DroolsParserException, IOException {
		new DroolsTest().test();
	}

	private void test() throws DroolsParserException, IOException {
		// TODO Auto-generated method stub
		RuleBase ruleBase = initialiseDrools();
		WorkingMemory workingMemory = initializeMessageObjects(ruleBase);
		int expectedNumberOfRulesFired = 1;

		int actualNumberOfRulesFired = workingMemory.fireAllRules();
	}

	private Reader getRuleFileAsReader(String ruleFile) {
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/test.drl");

		return new InputStreamReader(resourceAsStream);
	}

	private WorkingMemory initializeMessageObjects(RuleBase ruleBase) {
		WorkingMemory workingMemory = ruleBase.newStatefulSession();

		createHelloWorld(workingMemory);

		return workingMemory;
	}

	private void createHelloWorld(WorkingMemory workingMemory) {
		Message helloMessage = new Message();
		helloMessage.setType("Hello");
		workingMemory.insert(helloMessage);
	}

	private RuleBase addRulesToWorkingMemory(PackageBuilder packageBuilder) {
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		Package rulesPackage = packageBuilder.getPackage();
		ruleBase.addPackage(rulesPackage);

		return ruleBase;
	}

	private RuleBase initialiseDrools() throws IOException,
			DroolsParserException {
		PackageBuilder packageBuilder = readRuleFiles();
		return addRulesToWorkingMemory(packageBuilder);
	}

	private PackageBuilder readRuleFiles() throws DroolsParserException,
			IOException {
		PackageBuilder packageBuilder = new PackageBuilder();

		String ruleFile = "/Users/Vasu/Documents/workspace/DROOLS/src/main/resources/test.drl";
		Reader reader = getRuleFileAsReader(ruleFile);
		packageBuilder.addPackageFromDrl(reader);

		return packageBuilder;
	}
}
