#!/bin/bash

function main(){
	while getopts "f:d:m" opt; do
		case $opt in
			f) dataFile=$OPTARG;;
			m) merge=true;;
			d) dest=$OPTARG;;
		esac
	done

	if [[ -z $dataFile || -z $dest ]];
	then
		echo "Error! Missing required options! ex: -f srcFile -d destFile"
	else
		if [[ $merge = true ]]
		then
			echo "Filtering and appending data to '$dest'..."
			osmfilter $dataFile --keep="amenity=veterinary" | osmconvert - --all-to-nodes --csv="@id @lat @lon name" --csv-headline --csv-separator="," >> $dest
		else
			echo "Deleting previous and filtering new data into '$dest'..."
			osmfilter $dataFile --keep="amenity=veterinary" | osmconvert - --all-to-nodes --csv="@id @lat @lon name" --csv-headline --csv-separator="," -o="$dest"
		fi

		less $dest
	fi

}

main $@
