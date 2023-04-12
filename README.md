<h2>Проект "Биржевые цены"</h2>

REST API приложение для синхронизации биржевой стоимости акций топ 20 американский компаний.

#### Стек технологий: 
Java 17, REST, PostgreSQL, Hibernate, Spring Data JPA, Spring MVC, Maven, Git

---

<h3>Функционал приложения</h3>

* Сформировать перечень топ 20 компаний из индекса американских компаний S&P500 с данными о тикере компании, секторе и отрасли промышленности 
на основании данных, полученных с сайта https://finviz.com/screener.ashx?v=111&f=idx_sp500&o=-marketcap 
* С определенной периодичностью (используя аннотацию @Scheduled) обновлять и записывать в БД информацию о текущей стоимости акций компаний
вышеуказанного перечня, используя данные по ценам с ресурса Yahoo Finance.
* Предоставление API метода с выдачей постранично с page и size списка компаний с следующей информацией: тикер компании, название компании,
сектор и отрасль промышленности компании, последняя стоимость акций компании
* Предоставление API метода с предоставлением всей истории изменения стоимости акций одной из компаний
