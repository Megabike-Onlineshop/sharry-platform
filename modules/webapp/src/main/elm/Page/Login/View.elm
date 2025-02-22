module Page.Login.View exposing (view)

import Api
import Api.Model.OAuthItem exposing (OAuthItem)
import Comp.Basic as Basic
import Data.Flags exposing (Flags)
import Data.UiTheme exposing (UiTheme)
import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput, onSubmit)
import Markdown
import Messages.LoginPage exposing (Texts)
import Page exposing (Page(..))
import Page.Login.Data exposing (..)
import Styles as S


view : Texts -> Flags -> UiTheme -> Model -> Html Msg
view texts flags currentTheme model =
    div
        [ id "content"
        , class "h-full flex flex-col items-center justify-center w-full"
        , class S.content
        ]
        [ div [ class ("flex flex-col px-2 sm:px-4 py-4 rounded-md min-w-full md:min-w-0 md:w-96" ++ S.boxMd) ]
            [ div [ class "self-center" ]
                [ img
                    [ class "max-w-xs mx-auto max-h-20"
                    , if currentTheme == Data.UiTheme.Light then
                        src flags.config.logoUrl

                      else
                        src flags.config.logoUrlDark
                    ]
                    []
                ]
            , Html.form
                [ action "#"
                , onSubmit Authenticate
                , autocomplete False
                , classList
                    [ ( "hidden invisible", flags.config.oauthOnly ) ]
                ]
                [ div [ class "flex flex-col mt-6" ]
                    [ label
                        [ for "username"
                        , class S.inputLabel
                        ]
                        [ text texts.username
                        ]
                    , div [ class "relative" ]
                        [ div [ class S.inputIcon ]
                            [ i [ class "fa fa-user" ] []
                            ]
                        , input
                            [ type_ "text"
                            , name "username"
                            , autocomplete False
                            , onInput SetUsername
                            , value model.username
                            , autofocus Basics.True
                            , class ("pl-10 pr-4 py-2 rounded-lg" ++ S.textInput)
                            , placeholder texts.loginPlaceholder
                            ]
                            []
                        ]
                    ]
                , div [ class "flex flex-col my-3" ]
                    [ label
                        [ for "password"
                        , class S.inputLabel
                        ]
                        [ text texts.password
                        ]
                    , div [ class "relative" ]
                        [ div [ class S.inputIcon ]
                            [ i [ class "fa fa-lock" ] []
                            ]
                        , input
                            [ type_ "password"
                            , autocomplete False
                            , onInput SetPassword
                            , value model.password
                            , class ("pl-10 pr-4 py-2 rounded-lg" ++ S.textInput)
                            , placeholder texts.passwordPlaceholder
                            ]
                            []
                        ]
                    ]
                , div [ class "flex flex-col my-3" ]
                    [ button
                        [ type_ "submit"
                        , class S.primaryButton
                        ]
                        [ text texts.loginButton
                        ]
                    ]
                ]
            , if List.isEmpty flags.config.oauthConfig then
                div [] []

              else
                renderOAuthButtons texts flags model
            , resultMessage texts model
            , renderLangAndSignup texts flags
            ]
        , renderWelcome flags
        ]


renderLangAndSignup : Texts -> Flags -> Html Msg
renderLangAndSignup texts flags =
    div [ class "flex flex-row mt-6 items-center" ]
        [ div
            [ classList
                [ ( "hidden", flags.config.signupMode == "closed" )
                ]
            , class "flex flex-col flex-grow justify-end text-right text-sm opacity-75"
            ]
            [ span [ class "" ]
                [ text texts.noAccount
                ]
            , a
                [ class S.link
                , Page.href RegisterPage
                ]
                [ i [ class "fa fa-user-plus mr-1" ] []
                , text texts.signupLink
                ]
            ]
        ]


renderWelcome : Flags -> Html Msg
renderWelcome flags =
    case flags.config.welcomeMessage of
        "" ->
            span [ class "hidden invisible" ] []

        msg ->
            div [ class "flex flex-col px-2 sm:px-4 md:px-6 lg:px-8 py-4 max-w-md" ]
                [ div [ class "self-center" ]
                    [ Markdown.toHtml [] msg
                    ]
                ]


renderOAuthButtons : Texts -> Flags -> Model -> Html Msg
renderOAuthButtons texts flags _ =
    div
        [ class "w-full mt-2"
        ]
        [ if flags.config.oauthOnly then
            div [] []

          else
            Basic.horizontalDivider
                { label = texts.or
                , topCss = "w-full mb-4 hidden md:inline-flex"
                , labelCss = "px-4 bg-gray-200 bg-opacity-50"
                , lineColor = "bg-gray-300 dark:bg-slate-600"
                }
        , div
            [ class "flex flex-row space-x-2 flex-wrap items-center justify-center"
            , classList [ ( "mt-2", flags.config.oauthOnly ) ]
            ]
            (List.map (renderOAuthButton texts flags) flags.config.oauthConfig)
        ]


renderOAuthButton : Texts -> Flags -> OAuthItem -> Html Msg
renderOAuthButton texts flags item =
    let
        icon =
            Maybe.withDefault "fa fa-user" item.icon

        url =
            Api.oauthUrl flags item
    in
    a
        [ class S.primaryBasicButton
        , class "mt-1"
        , href url
        ]
        [ i [ class icon ] []
        , span [ class "ml-2" ]
            [ text (texts.via ++ " " ++ item.name)
            ]
        ]


resultMessage : Texts -> Model -> Html Msg
resultMessage texts model =
    case model.result of
        Just r ->
            if r.success then
                div
                    [ class S.successMessage
                    , class "mt-2"
                    ]
                    [ text texts.loginSuccessful
                    ]

            else
                div
                    [ class S.errorMessage
                    , class "mt-2"
                    ]
                    [ text r.message
                    ]

        Nothing ->
            span [] []
