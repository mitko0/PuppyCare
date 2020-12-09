@echo off

osmfilter %1 --keep="amenity=veterinary" | osmconvert64 - --all-to-nodes --csv="@id @lat @lon" --csv-separator="," | pgdb -u %2 -p %3

pause
