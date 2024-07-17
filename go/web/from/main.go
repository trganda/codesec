package main

import (
	"fmt"
	"net/http"
)

func main() {
	http.HandleFunc("/request", func(w http.ResponseWriter, r *http.Request) {
		r.ParseForm()
		fmt.Fprintf(w, "RawQuery: %s\n", r.URL.RawQuery)
		fmt.Fprintf(w, "RawQuery: %s\n", r.URL.Query())
		fmt.Fprintf(w, "RawQuery: %s\n", r.Form)
		fmt.Fprintf(w, "RawQuery: %s\n", r.PostForm)
	})

	http.Handle("/", http.FileServer(http.Dir("./")))

	http.ListenAndServe(":80", nil)
}
