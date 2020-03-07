<#include "security.ftl">
<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark-light ">
    <div class="container">
        <a class="navbar-brand" href="/">Seed</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav">
                <#if loggedUser??>
                    <li class="nav-item">
                        <a class="nav-link" href="/main">Home</a>
                    </li>
                </#if>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/user">User list</a>
                    </li>
                </#if>
            </ul>
                <form class="form-inline my-2 col-centered" method="get">
                        <input class="form-control form-control-sm mr-sm-2" type="search" name="filter"
                               placeholder="tag" <#if filter??>value="${filter}"</#if>/>
                    <button class="btn btn-primary my-2 my-sm-0" type="submit"><i class="fas fa-search fa-1x"></i></button>
                </form>
            <#if loggedUser??>
                <form class="form-inline my-2" action="/logout" method="post">
                    <a class="nav-link navbar-text" href="/profile/${currentUserId}">${name}</a>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <input class="btn btn-light ml-2 btn-sm" type="submit" value="Sign Out"/>
                </form>
            <#else>
                <a href="/login" class="btn btn-light ml-2 btn-sm">Log in</a>
            </#if>
        </div>
    </div>

</nav>
