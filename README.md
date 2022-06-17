# Project 3 - **Parsetagram**

**Parsetagram** is a photo sharing app using Parse as its backend.

Time spent: **25** hours spent in total

## User Stories

The following **required** functionality is completed:

- [X] User sees app icon in home screen.
- [X] User can sign up to create a new account using Parse authentication
- [X] User can log in to their account
- [X] The current signed in user is persisted across app restarts
- [X] User can log out of their account
- [X] User can take a photo, add a caption, and post it to "Instagram"
- [X] User can view the last 20 posts submitted to "Instagram"
- [X] User can pull to refresh the last 20 posts submitted to "Instagram"
- [X] User can tap a post to go to a Post Details activity, which includes timestamp and caption.

The following **stretch** features are implemented:

- [X] Style the login page to look like the real Instagram login page.
- [ ] Style the feed to look like the real Instagram feed.
- [X] User can load more posts once they reach the bottom of the feed using endless scrolling.
- [X] User should switch between different tabs using fragments and a Bottom Navigation View.
    - [X] Feed Tab (to view all posts from all users)
    - [X] Capture Tab (to make a new post using the Camera and Photo Gallery)
    - [ ] Profile Tab (to view only the current user's posts, in a grid)
- [X] Show the username and creation time for each post
- User Profiles:
    - [ ] Allow the logged in user to add a profile photo
    - [ ] Display the profile photo with each post
    - [ ] Tapping on a post's username or profile photo goes to that user's profile page
    - [ ] User Profile shows posts in a grid
- [ ] After the user submits a new post, show an indeterminate progress bar while the post is being uploaded to Parse
- [X] User can comment on a post and see all comments for each post in the post details screen.
- [X] User can like a post and see number of likes for each post in the post details screen.

The following **additional** features are implemented:

- [X] Added Instagram icon to home page
- [X] Resize each photo before uploading to Parse
- [X] Instagram like button turns red upon a user like

Please list two areas of the assignment you'd like to **discuss further with your peers** during the next class (examples include better ways to implement something, how to extend your app in certain ways, etc):

1. Allowing each user to add a bio to their profile.
2. Showing a recyclerview of people who liked a certain post.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [Kap](https://getkap.co/).

## Credits

List an 3rd party libraries, icons, graphics, or other assets you used in your app.

- [Android Async Http Client](http://loopj.com/android-async-http/) - networking library


## Notes

Describe any challenges encountered while building the app.

I had a lot of difficulties working with the emulator and parse. On a few occasions, my emulator
didn't reflect the changes made - and I had to wipe the data off of the emulator. I also had a few 
challenges working with the bottom navigation bar and implementing each fragment.

## License

    Copyright 2022 Tina Wen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.