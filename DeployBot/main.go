package main

import (
	"DeployBot/awslex"
	//"DeployBot/controller"
	"DeployBot/mattermostapi"
	"log"
	"os"
	"os/signal"
	"strings"
	"syscall"

	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/lexruntimeservice"
	"github.com/mattermost/mattermost-server/model"
)

var client *model.Client

func main() {

	//go controller.Start()
	mm := &mattermostapi.MatterMost{
		Url:         "http://localhost:8065",
		UserName:    "veli",
		Password:    "12345",
		TeamName:    "Devops",
		ChannelName: "DevopsBot",
	}

	client := mm.GetClient()
	log.Println(" Channle Id in main " + mm.ChannelId)
	webSocketClient, err := model.NewWebSocketClient4("ws://localhost:8065", client.AuthToken)
	if err != nil {
		println("We failed to connect to the web socket")
		log.Fatal(err)
	}

	webSocketClient.Listen()

	go func() {
		for {
			select {
			case resp := <-webSocketClient.EventChannel:
				HandleResponse(resp, mm)
			}
		}
	}()

	sigterm := make(chan os.Signal, 1)
	signal.Notify(sigterm, syscall.SIGTERM)
	signal.Notify(sigterm, syscall.SIGINT)
	<-sigterm

}

func HandleResponse(event *model.WebSocketEvent, mc *mattermostapi.MatterMost) {

	log.Println("HandleResponse - " + event.Broadcast.ChannelId)
	if event.Broadcast.ChannelId != mc.ChannelId {
		return
	}
	if event.Event != model.WEBSOCKET_EVENT_POSTED {
		return
	}

	println("responding to message in channel ", mc.ChannelName)

	log.Println("responding to debugging channel msg")

	postedmessage := model.PostFromJson(strings.NewReader(event.Data["post"].(string)))

	if postedmessage != nil {

		if postedmessage.UserId == mc.UserId {
			return
		}

		ProcessMessage(postedmessage, mc)

	}

}

func ProcessMessage(postedmessage *model.Post, mc *mattermostapi.MatterMost) {

	log.Println("Posted message for - " + postedmessage.Message + " by user " + postedmessage.UserId)
	res, _ := mc.GetClient().GetUser(postedmessage.UserId, mc.GetClient().Etag)
	username := res.Data.(*model.User).GetFullName()
	input := new(lexruntimeservice.PostTextInput)
	input.SetBotAlias("devopsbot")
	input.SetBotName("devopsbot")
	input.SetInputText(postedmessage.Message)
	input.SetUserId(postedmessage.UserId)
	mySession, _ := session.NewSession(&aws.Config{
		Region: aws.String("us-east-1")})

	output, _ := awslex.GetLexOutput(input, mySession)
	dialogstate := aws.StringValue(output.DialogState)

	log.Println("Dialog state ", dialogstate)
	log.Println("message " + aws.StringValue(output.Message))
	switch dialogstate {
	case "ElicitIntent":
		mc.PostMessage(mc.GetClient(), username+" - "+aws.StringValue(output.Message))
	case "ElicitSlot":
		mc.PostMessage(mc.GetClient(), username+" -  "+aws.StringValue(output.Message))
	case "ReadyForFulfillment":
		intent := aws.StringValue(output.IntentName)
		log.Println("intent name " + intent)
		messageToSend := BackendLogic(output.Slots, intent)
		log.Println("AppName " + aws.StringValue(output.Slots["App"]))
		mc.PostMessage(mc.GetClient(), username+" - "+messageToSend)

	}
}

func BackendLogic(inputs map[string]*string, intent string) string {

	switch intent {
	case "DeployInfo":
		log.Println("AppName " + aws.StringValue(inputs["App"]))
		return "Deployed version of " + aws.StringValue(inputs["App"]) + " in " + aws.StringValue(inputs["Env"]) + " is 100"

	case "Schedules":
		return "Scheduled Deployment at 10 PM"

	}

	return ""
}
