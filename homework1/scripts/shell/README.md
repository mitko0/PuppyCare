# Инструкции за стартување на скрипта

Скриптата која ја користиме претставува `linux bash` скрипта.
За нејзино успешно извршување потребо е на системот да имаме 
инсталирано _Osmfilter_ кој на може да се инсталира со командата
`sudo apt install osmctools`.

Скриптата има 3 опции од кои 2 се задолжителни:
- \-f -- ја означува изворната датотека [.osm наставка] од која се исчитуваат сите податоци (задолжителна опција)
- \-d -- ја означува дестинациската датотека [.csv наставка] каде ќе се запишаат филтрираните податоци (задолжителна опција)
- \-m -- означува дали податоците во дестинациската датотека ќе се пребрипат или не

## Пример
`./filter_vets -f ../data/macedona.osm -d ../data/vets.csv`