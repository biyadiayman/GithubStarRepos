package com.example.ayman.githubstarrepos

//List of all repositories
class RepoFeed(val items: List<item>)

//one repository
class item(val name: String, val description: String,val stargazers_count: Int, val owner: Owner)

//owner of a repo
class Owner(val login: String, val avatar_url: String)