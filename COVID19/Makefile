APPLICATION_ID = com.vbstudio.covid19
RUN_COMMAND = adb shell am start -n $(APPLICATION_ID)/$(APPLICATION_ID).home.ActivityHome

offline?=true
variant?=iD

ifeq ($(offline), true)
	OFFLINE_TOKEN= --offline
endif

install:
	./gradlew $(OFFLINE_TOKEN) $(variant) && $(RUN_COMMAND)