package lib
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint


import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import groovy.time.TimeCategory

import internal.GlobalVariable

import MobileBuiltInKeywords as Mobile
import WSBuiltInKeywords as WS
import WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import kms.turing.katalon.plugins.helper.table.HTMLTableHelper
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.util.internal.PathUtil as PathUtil

class Actions {
	/**
	 * Actions on web elements
	 */

	int waitTime = GlobalVariable.defaultWaitTime

	@Keyword
	def click(TestObject element) {
		wait(element)
		WebUI.waitForElementClickable(element, waitTime)
		WebUI.click(element)
	}


	@Keyword
	def tap(TestObject element) {
		wait(element)
		Mobile.tap(element)
	}


	@Keyword
	def sendKeys(TestObject element, String text) {
		wait(element)
		WebUI.waitForElementClickable(element, waitTime)
		WebUI.clearText(element)
		WebUI.sendKeys(element, text)
	}

	@Keyword
	def sendKeysEncrypted(TestObject element, String encryptedText) {
		wait(element)
		WebUI.waitForElementClickable(element, waitTime)
		WebUI.setEncryptedText(element, encryptedText)
	}

	@Keyword
	def getText(TestObject element, int maxWaitTime = waitTime) {
		wait(element, maxWaitTime)
		return WebUI.getText(element).trim()
	}

	@Keyword
	def wait(TestObject element, int maxWaitTime = waitTime) {
		WebUI.waitForElementPresent(element, maxWaitTime)
		WebUI.waitForElementVisible(element, maxWaitTime)
	}

	@Keyword
	def getElementCount(TestObject element, int waitTimeLocal = 5) {

		def count
		if(!(WebUI.verifyElementPresent(element, waitTimeLocal, FailureHandling.OPTIONAL))) {
			count = 0
		}
		else {
			count = WebUiCommonHelper.findWebElements(element, waitTime).size()
		}
		return count
	}

	@Keyword
	def getColumnIndex(List headers, String columName){

		WebElement table = HTMLTableHelper.identifyTableByColumnHeaders(headers, 10,  FailureHandling.CONTINUE_ON_FAILURE)
		return HTMLTableHelper.getColumnIndexByHeader(table, columName, FailureHandling.STOP_ON_FAILURE)
	}

	@Keyword
	def getUrlAndVerify(String name, String failuredescription ) {
		if(!(WebUI.getUrl().endsWith(name))) {
			WebUI.takeScreenshot()
			KeywordUtil.markFailed(failuredescription)
		}
	}


	@Keyword
	public static removeFileFromFolder() {

		def downloadsFolder = PathUtil.relativeToAbsolutePath("downloads", RunConfiguration.getProjectDir())
		File folder = new File(downloadsFolder)
		for (File file : folder.listFiles()) {
			if(file.getName()=="fileName.apk") {
				file.delete()
				break
			}
		}
	}
}






