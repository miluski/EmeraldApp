### RecruitmentTask

Wersja live znajduje się na stronie: https://emerald-app-88c863e81f66.herokuapp.com

Dokonane założenia:

- Każdy sprzedawca musi mieć konto w serwisie, aby znać swój stan konta i aby ten stan konta był aktualizowany przy każdym dodaniu kampanii marketingowej produktu.

- Tylko i wyłącznie zalogowany użytkownik może mieć dostęp do dodawania, edycji lub usuwania kampanii marketingowych. Stąd zabezpieczenie endpointów za pomocą tokenów JWT oraz panel logowania na samym początku.

- Każdy użytkownik ma dostęp wyłącznie do własnej puli kampanii, które utworzy.

- Edycja kampanii jest możliwa po kliknięciu przycisku edytuj w tabeli.

- Bid amount zostało potraktowane jako informacyjne pole informujące o cenie reklamowanego produktu (minimalnie 1 zł).

- Minimalny fundusz kampanii został ustawiony na 500 zł.

- Przy ustaleniu nowego funduszu kampanii jest on pobierany na nowo z konta, ponieważ traktowane to jest jako odnowienie kampanii.

- Konta utworzone do testowania działania aplikacji to:

```

1 konto:

username: sprzedawca1
password: Haslo@1234

2 konto:

username: sprzedawca2
password: Haslo@4321

```

