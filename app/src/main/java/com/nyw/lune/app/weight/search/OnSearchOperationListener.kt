package com.nyw.lune.app.weight.search

interface OnSearchOperationListener {
    fun onSearchResult(result: String)
    fun onContentChanged(content: String)
}