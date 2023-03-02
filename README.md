<b>Rosu Mihai Cosmin 323CA</b>

<b>Etapa 1</b>

In cadrul rezolvarii temei, pentru implementarea citirii datelor, am folosit
modul in care a fost citit input-ul in prima tema. De asemenea, am folosit
design pattern-uri de strategy, factory, singleton si observer.

<b>Flow-ul simularii:</b>  
Citirea datelor se poate face in clasa Main sau in clasa Test. Clasa Main va
trece prin toate testele, iar clasa Test va trece printr-un singur test cu
numele citit de la tastatura.

Rezolvarea efectiva a unui test are loc in functia runTest din clasa Main.
- runTest:
  - Folosind un JSONParser se preia continutul fisierului test si se imparte
    in mai multe bucati ce sunt stocate in memorie in clase corespunzatoare.
  - Dupa aceea, se pregateste un JSONArray pentru output si se apeleaza functia
    simulate din clasa Santa.

- simulate:
  - Simularea incepe prin stergerea de pe lista a copiilor cu varsta de peste
    18 ani, iar pentru cei ramasi este calculat scorul de cumintenie folosind
    o strategie de calcul, care este aleasa de catre factory-ul (care este de
    tip singleton) de strategii prin functia createStrategy care primeste
    varsta copilului si intoarce o strategie corespunzatoare.
  - Folosind scorurile de cumintenie ale tuturor copiilor se calculeaza suma
    acestora pentru ca apoi sa se calculeze budget unit-ul lui Mos Craciun.
  - Dupa acest calcul fiecarui copil i se asigneaza un buget in functie de cat
    de cuminte a fost.
  - Folosind acest buget, Mos Craciun incearca sa ii cumpere copilului cate un
    cadou din fiecare categorie din lista de preferinte a acestuia, cu conditia
    ca Mos Craciun sa aiba cel putin un cadou din acea categorie in lista lui
    de cadouri (daca sunt mai multe este ales cel mai ieftin).
  - Dupa ce ii este parcursa lista de preferinte, observer-ul copilului (clasa
    Child este de tip Observable si fiecare copil are un OutputMessage care
    implementeaza interfata Observer) este notificat.
  - In continuare, daca s-a atins numarul de ani se iese din functie, altfel se
    aplica schimbarile din annual change-ul corespunzator anului curent (se
    schimba bugetul lui Mos Craciun, se adauga copiii noi si cadourile noi, si
    se actualizeaza copiii), apoi se reapeleaza functie pentru anul urmator.

- OutputMessage:
  - In cadrul observer-ului se creeaza un JSONObject in care se adauga toate
    detaliile despre copilul de la care a venit notificarea observer-ului.
  - Dupa ce au fost introduse detaliile, JSONObject-ul este adaugat in
    JSONArray-ul folosit pentru output.

Dupa ce termina de rulat simularea din clasa Santa, JSONArray-ul rezultat este
adaugat intr-un JSONObject si este afisat sub forma unui String in fisierul de
output corespunzator testului.

<b>Etapa 2</b>

In primul rand, am adaugat campurile necesare claselor care prezentau
modificari, precum: niceScoreBonus, elf si niceScoreCity clasei Child, quantity
clasei Gift, cityStrategy clasei Change si elf clasei ChildUpdate.
- niceScoreCity este un hashMap in care cheia este un oras (enum Cities), iar
valoarea este scorul de cumintenie mediu al copiilor din acel oras (Double).

<b>Main:</b>
- In clasa Main, singurele modificari sunt cele facute pentru citirea datelor,
care sunt prezente si in clasa Helpers, in functiile folosite la citire.

<b>Santa:</b>
- constructor:
  - In constructor am adaugat ca strategia de impartire a cadourilor sa fie cea
  in functie de id-uri, intrucat se presupune ca in anul 0 copiii sunt sortati
  dupa id-uri.
- simulate:
  - In primul rand preiau instanta factory-ului de comparatoare folosite pentru
  sortarea copiilor (factory-ul intoarce un comparator nou creat pe baza
  cityStrategy-ului primit ca parametru).
  - Apoi, inainte sa fie calculat budgetUnit-ul mosului, am sortat copiii dupa
  id, pentru a nu se crea erori de calcul din cauza ordinii copiilor. De
  asemenea, pentru calculul scorului de cumintenie al unui copil se tine cont
  si de niceScoreBonus-ul fiecarui copil, dar i se limiteaza scorul la 10.
  - Inainte sa sortez copiii, trebuie sa pregatesc hashMap-ul cu scorurile de
  cumintenie ale oraselor (pentru fiecare oras, parcurg copiii, iar pentru cei
  care sunt din orasul respectiv calculez media scorului de cumintenie). Dupa
  ce trec prin toate orasele actualizez hashMap-ul din cadrul fiecarui copil.
  - Dupa aceea, sortez copiii dupa comparatorul generat de factory pe baza
  cityStrategy-ului.
  - In cadrul impartirii cadourilor, cand se calculeaza bugetul fiecarui copil,
  in functie de elful copilului se modifica bugetul corespunzator.
  - Cand se cauta cadouri pentru copil, se verifica sa mai existe acel cadou (sa
  aiba o cantitate mai mare ca 0) si dupa ce i se da cadoul copilului,
  cantitatea acestuia este decrementata.
  - Dupa ce s-a terminat impartirea cadourilor, copiii sunt sortati dupa id
  pentru a incepe impartirea cadourilor facuta de elful yellow. Pentru fiecare
  copil se verifica daca elful lui este yellow si daca nu a primit niciun
  cadou. In acest caz, se cauta cel mai ieftin cadou din categoria preferata
  si, daca este gasit si are o cantitate mai mare ca 0, ii este dat copilului.
  - In final, cand se aplica annualChange-ul, se aplica si noile modificari
  adaugate in etapa 2.
