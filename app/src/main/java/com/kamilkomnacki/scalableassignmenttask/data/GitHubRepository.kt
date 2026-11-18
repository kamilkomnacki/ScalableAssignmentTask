package com.kamilkomnacki.scalableassignmenttask.data

import com.google.gson.JsonParseException
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo
import com.kamilkomnacki.scalableassignmenttask.data.remote.GitHubApi
import java.io.IOException

class GitHubRepository(
    private val api: GitHubApi,
    private val repositoriesUrl: String,
) {
    suspend fun getRepos(): List<Repo> {
        val response = try {
            api.getRepos(repositoriesUrl)
        } catch (e: IOException) {
            throw GitHubFailure.Network(e)
        } catch (e: JsonParseException) {
            invalidResponse(e)
        }

        if (!response.isSuccessful) {
            throw GitHubFailure.Unavailable()
        }

        return response.body()
            ?.map { repo -> repo?.toRepo() ?: invalidResponse() }
            ?: invalidResponse()
    }
}
