O objetivo do desafio proposto é avaliar o conhecimento do candidato e verificar se ele possui o conhecimento básico esperado para exercício das atividades esperadas.

#Desafio
O desafio consiste na construção de um aplicativo  simples para exibição de previsão do tempo na plataforma Android. O objetivo do app é exibir a previsão do tempo para uma determinada região pré configurada pelo usuário. Para aquisição dos dados meteorológicos podem ser utilizada a API fornecida pelo OpenWeatherMap ou qualquer outro que você conheça. 

##Requisitos
* O app deve ter no minimo 3 telas. Uma com o overview das previsões no período de uma semana, detalhamento sobre uma previsão e configuração
* A principal tela do aplicativo ( tela onde é exibida o overview das previsões ) deve ser suportadas em tablets e handset (smarthphone);
* O aplicativo deve requisitar os dados de um servidor remoto;
* O aplicativo deve permitir que o usuário configure a região no qual deseja saber a previsão do tempo;
* A API minima do aplicativo deve ser 18.
* Testes unitários.

#Plus
* O aplicativo deve lançar notificações diárias com as previsões para o dia atual
* Todas as telas suportadas em tablets e handset

#Observações
* Não reinvente a roda. Aproveite o máximo que a plataforma pode lhe oferecer.
* Todas as imagens utilizadas do aplicativo podem ser adquiridas na internet
* Não se preocupe em deixar o aplicativo com um uma interface profissional para publicação. Faça o necessário para deixar o app mais simples possível utilizando bem o espaço de tela.
* De prioridade aos REQUISITOS e não ao plus

#O que sera avaliado
* Qualidade do código ( clareza, boas práticas )
* Desempenho do aplicativo
* Entrega no prazo
* Melhor utilização das API fornecidas pela plataforma
* Melhor utilização do espaço da tela

#Entrega
**Deverá criar um PR deste projeto com a solução apresentada**

**Qualquer duvida entrar em contato através do email dev (at) itslemonade.com**

##Observações sobre o PR:
* Build disponível [neste link](http://thecoreme.org/shared/DesafioAndroid/app-debug.apk)

* Utilizei a biblioteca [WeatherLib](https://survivingwithandroid.github.io/WeatherLib/), me baseando também no app demo [demo](https://github.com/survivingwithandroid/WeatherLib/tree/master/demo).

  Tal biblioteca prove acesso a diversos provedores, entre eles [Forecast.io](https://forecast.io), [Yahoo! Weather](https://weather.yahoo.com/) e [OpenWeatherMap](http://openweathermap.org/), sendo que que escolhi este último para o app.

* O requisito do teste unitário não foi feito.

* Só é feita a exibição de textos no app, sem nenhuma imagem.

* O app é composto de duas activities, sendo a principal para o overview e detalhamento do dia atual (que são Fragments), e a tela de configurações. Apesar do botão de voltar não estar sendo exibido, ao utilizar a tecla BACK do dispositivo é feita a correta transição entre telas.

* É apenas possível ver o detalhamento do clima do dia atual, ao clicar-se no primeito item da tela
de overview ("Today").

* A tela de configuração apenas permite a busca e definição da cidade, porém poderia ser
adicionado configuração do sistema de unidades (métrico/imperial, deixei métrico como padrão),
número de dias a ser exibido no overview (forecast, padrão = 6) e limite do número de cidades
resultantes da busca.

* Os resultados da busca e o termo usado para tal não estão sendo guardados quando ocorre algum evento no dispositivo, como rotação de tela, por exemplo.

* Primeira vez utilizando Android Studio, possível que esqueci de fazer o commit de algum arquivo essencial :p
