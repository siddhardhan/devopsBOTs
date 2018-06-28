package awslex

import (
	"log"

	"github.com/aws/aws-sdk-go/aws"

	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/lexruntimeservice"
)

func GetLexOutput(input *lexruntimeservice.PostTextInput, mysession *session.Session) (output *lexruntimeservice.PostTextOutput, err error) {

	log.Println(" Lex - Bot Alias" + aws.StringValue(input.BotAlias))
	log.Println(" Lex - Bot Alias" + aws.StringValue(input.UserId))

	svc := lexruntimeservice.New(mysession)
	output, err = svc.PostText(input)
	if err != nil {
		println("Error", err.Error())
	}
	return
}
