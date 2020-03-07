<#import "components/index.ftl" as i>

<@i.page>
<table class="col-centered mb-3">
    <tbody>
    <tr>
        <td class="p-2"><h4>${user.username}</h4></td>
        <#if isCurrentUser>
            <td><a class="btn btn-primary my-2 my-sm-0" href="/user/edit">Edit profile</a></td>
        <#elseif isSubscriber>
            <td><a class="btn btn-primary my-2 my-sm-0" href="/user/unsubscribe/${userId}">Unsubscribe</a></td>
        <#else>
            <td><a class="btn btn-primary my-2 my-sm-0" href="/user/subscribe/${userId}">Subscribe</a></td>
        </#if>


    </tr>
    <tr>
        <td class="p-2"><b>${postsCount}</b> posts</td>
        <td class="p-2"><b>${subscriptionsCount}</b> subscriptions</td>
        <td class="p-2"><b>${subscribersCount}</b> subscribers</td>
    </tr>
    </tbody>
</table>

<#if posts?has_content>
    <#include "components/messageList.ftl" />
<#else>
    <div class="alert alert-primary col-md-6 col-centered" role="alert">
        No posts yet
    </div>
</#if>

</@i.page>