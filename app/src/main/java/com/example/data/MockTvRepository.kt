package com.example.data

import com.example.R

object MockTvRepository {

    val channels = listOf(
        Channel(
            id = "abass_main",
            name = "ABASS TV News",
            tagline = "Voice of Truth & Excellence",
            category = "News & Live",
            streamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            currentShow = "ABASS Evening Prime Live",
            currentShowTime = "20:00 - 21:30",
            viewerCount = 18450,
            imageResId = R.drawable.img_hero_live_1793121474898
        ),
        Channel(
            id = "abass_cinema",
            name = "ABASS Cinema",
            tagline = "Premier Nollywood & Global Films",
            category = "Movies & Drama",
            streamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            currentShow = "Echoes of the Soil (Premiere)",
            currentShowTime = "19:30 - 22:00",
            viewerCount = 11280,
            imageResId = R.drawable.img_cinema_banner_1793121492865
        ),
        Channel(
            id = "abass_sports",
            name = "ABASS Sports",
            tagline = "Champions & African Stars",
            category = "Sports",
            streamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            currentShow = "Matchday Live: Continental Cup Recap",
            currentShowTime = "21:00 - 22:30",
            viewerCount = 15920,
            imageResId = R.drawable.img_hero_live_1793121474898
        ),
        Channel(
            id = "abass_culture",
            name = "ABASS Culture",
            tagline = "African Heritage, Music & Rhythms",
            category = "Culture & Music",
            streamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            currentShow = "Sounds of the Savannah",
            currentShowTime = "18:00 - 19:30",
            viewerCount = 7430,
            imageResId = R.drawable.img_cinema_banner_1793121492865
        ),
        Channel(
            id = "abass_noor",
            name = "ABASS Noor & Faith",
            tagline = "Light of Wisdom & Community",
            category = "Spiritual",
            streamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            currentShow = "Reflections on Life & Serenity",
            currentShowTime = "20:00 - 21:00",
            viewerCount = 9140,
            imageResId = R.drawable.ic_abass_logo_1793121431672
        )
    )

    val programs = listOf(
        Program(
            id = "prog_1",
            channelId = "abass_main",
            title = "Sunrise Africa Morning Show",
            description = "Start your day with newspaper reviews, economic outlook, traffic updates, and exclusive interviews with thought leaders.",
            startTime = "06:00",
            endTime = "09:00",
            durationMinutes = 180,
            progressPercent = 1.0f,
            day = "Today",
            genre = "Morning News",
            host = "Khadija Abass & David Mensah",
            isLiveNow = false,
            hasCatchUp = true
        ),
        Program(
            id = "prog_2",
            channelId = "abass_main",
            title = "Midday Global Briefing",
            description = "Comprehensive international and regional news, diplomatic updates, market indexes, and live correspondents worldwide.",
            startTime = "12:00",
            endTime = "13:30",
            durationMinutes = 90,
            progressPercent = 1.0f,
            day = "Today",
            genre = "International News",
            host = "Amara Diallo",
            isLiveNow = false,
            hasCatchUp = true
        ),
        Program(
            id = "prog_3",
            channelId = "abass_main",
            title = "ABASS Evening Prime Live",
            description = "Flagship evening broadcast breaking down top investigative reports, politics, community affairs, and live interviews.",
            startTime = "20:00",
            endTime = "21:30",
            durationMinutes = 90,
            progressPercent = 0.45f,
            day = "Today",
            genre = "Live Flagship",
            host = "Abass Umar & Zainab Lawal",
            isLiveNow = true,
            hasCatchUp = false
        ),
        Program(
            id = "prog_4",
            channelId = "abass_main",
            title = "Nightline Roundtable Discussion",
            description = "Deep dive analytical debates featuring political strategists, youth innovators, and civil society advocates.",
            startTime = "22:00",
            endTime = "23:00",
            durationMinutes = 60,
            progressPercent = 0f,
            day = "Today",
            genre = "Debate",
            host = "Ibrahim Bello",
            isLiveNow = false,
            hasCatchUp = false
        ),
        Program(
            id = "prog_5",
            channelId = "abass_cinema",
            title = "Echoes of the Soil (World Premiere)",
            description = "Acclaimed drama exploring heritage, struggle, family bonds, and resilience in a fast-changing world.",
            startTime = "19:30",
            endTime = "22:00",
            durationMinutes = 150,
            progressPercent = 0.60f,
            day = "Today",
            genre = "Drama / Cinema",
            host = "Sankofa Films Presentation",
            isLiveNow = true,
            hasCatchUp = false
        ),
        Program(
            id = "prog_6",
            channelId = "abass_cinema",
            title = "Midnight Thriller: Shadows in the City",
            description = "A suspenseful detective thriller following an inspector tracking an enigmatic syndicate through neon alleys.",
            startTime = "22:15",
            endTime = "00:30",
            durationMinutes = 135,
            progressPercent = 0f,
            day = "Today",
            genre = "Suspense",
            host = "Nollywood Showcase",
            isLiveNow = false,
            hasCatchUp = false
        ),
        Program(
            id = "prog_7",
            channelId = "abass_sports",
            title = "Matchday Live Arena",
            description = "Instant highlights, pitch-side reactions, VAR debates, and tactical masterclasses from international fixtures.",
            startTime = "21:00",
            endTime = "22:30",
            durationMinutes = 90,
            progressPercent = 0.30f,
            day = "Today",
            genre = "Sports Live",
            host = "Captain Segun & Tolu",
            isLiveNow = true,
            hasCatchUp = false
        ),
        Program(
            id = "prog_8",
            channelId = "abass_main",
            title = "Investigative Africa: Tech & Agro-Revolution",
            description = "How young innovators across the continent are transforming farming and logistics with satellite data.",
            startTime = "10:00",
            endTime = "11:30",
            durationMinutes = 90,
            progressPercent = 0f,
            day = "Tomorrow",
            genre = "Documentary",
            host = "Dr. Amina Touré",
            isLiveNow = false,
            hasCatchUp = false
        ),
        Program(
            id = "prog_9",
            channelId = "abass_main",
            title = "Presidential Press Hour",
            description = "Live questions from the presidential villa briefing room on infrastructure, foreign direct investment, and trade.",
            startTime = "14:00",
            endTime = "15:30",
            durationMinutes = 90,
            progressPercent = 0f,
            day = "Tomorrow",
            genre = "Special Broadcast",
            host = "ABASS News Special",
            isLiveNow = false,
            hasCatchUp = false
        )
    )

    val vodVideos = listOf(
        VodVideo(
            id = "vod_1",
            title = "Echoes of the Soil: Exclusive Director Cut & Cast Interview",
            category = "Cinema",
            duration = "1h 48m",
            views = "242K views",
            date = "2 days ago",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            imageResId = R.drawable.img_cinema_banner_1793121492865,
            description = "Watch the full festival-winning motion picture exploring resilience, family honor, and cultural heritage.",
            badge = "PREMIERE"
        ),
        VodVideo(
            id = "vod_2",
            title = "ABASS Special Report: Renewable Energy Boom Across West Africa",
            category = "Documentary",
            duration = "42:15",
            views = "89K views",
            date = "Yesterday",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            imageResId = R.drawable.img_hero_live_1793121474898,
            description = "In-depth investigation on solar farms, mini-grids, and how rural communities are leaping into green energy.",
            badge = "EXCLUSIVE"
        ),
        VodVideo(
            id = "vod_3",
            title = "Nollywood Gala Night: Red Carpet Highlights & Awards",
            category = "Entertainment",
            duration = "28:50",
            views = "135K views",
            date = "3 days ago",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            imageResId = R.drawable.img_cinema_banner_1793121492865,
            description = "Glamour, celebrity interviews, and top winners from the continental film excellence gala.",
            badge = "TRENDING"
        ),
        VodVideo(
            id = "vod_4",
            title = "Continental Football Derby: Top 10 Goals of the Season",
            category = "Sports",
            duration = "16:20",
            views = "310K views",
            date = "4 days ago",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            imageResId = R.drawable.img_hero_live_1793121474898,
            description = "Relive the thunderous long-range strikes, acrobatic volleys, and stoppage-time match winners.",
            badge = "POPULAR"
        ),
        VodVideo(
            id = "vod_5",
            title = "Youth Entrepreneurship Forum: Building Global Tech Giants",
            category = "Business",
            duration = "36:40",
            views = "64K views",
            date = "5 days ago",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            imageResId = R.drawable.ic_abass_logo_1793121431672,
            description = "Founders discuss scaling startups, raising seed capital, and solving logistics challenges.",
            badge = null
        )
    )

    val initialChatMessages = listOf(
        ChatMessage(
            id = "msg_0",
            sender = "ABASS Studio Bot",
            city = "HQ Broadcast Desk",
            message = "🔴 Welcome to ABASS TV Live! Join the conversation from anywhere in the world.",
            timestamp = "20:00",
            isModerator = true,
            reactionsCount = 42
        ),
        ChatMessage(
            id = "msg_1",
            sender = "Alhaji Musa",
            city = "Kano",
            message = "Crystal clear picture quality today from Kano! Well done ABASS TV team.",
            timestamp = "20:02",
            isModerator = false,
            reactionsCount = 18
        ),
        ChatMessage(
            id = "msg_2",
            sender = "Chidinma O.",
            city = "Lagos",
            message = "Echoes of the Soil movie was deeply touching. The cinematography is world class! 🔥",
            timestamp = "20:04",
            isModerator = false,
            reactionsCount = 29
        ),
        ChatMessage(
            id = "msg_3",
            sender = "Kwame Asante",
            city = "Accra",
            message = "Watching live from Ghana 🇬🇭 Big salute to the news anchors!",
            timestamp = "20:06",
            isModerator = false,
            reactionsCount = 15
        ),
        ChatMessage(
            id = "msg_4",
            sender = "Fatima Z.",
            city = "London",
            message = "Streaming on the mobile app smoothly here in the UK. Love the new player design!",
            timestamp = "20:08",
            isModerator = false,
            reactionsCount = 22
        )
    )

    val newsAlerts = listOf(
        NewsAlert(
            id = "news_1",
            headline = "African Free Trade Agreement Records Record Cross-Border Volume This Quarter",
            fullStory = "Trade ministers announced historic milestone figures today as tariff reductions on agricultural and manufacturing commodities boosted trade activity by over 34% across member nations, signaling strong regional economic resilience.",
            category = "Economy",
            timeAgo = "12m ago",
            isBreaking = true
        ),
        NewsAlert(
            id = "news_2",
            headline = "ABASS TV Unveils New High-Definition Satellite Transponder on Badr 7",
            fullStory = "Viewers across West Africa, North Africa, and Southern Europe can now enjoy uninterrupted 1080p Full HD broadcast on Satellite Badr 7 frequency 11595 Vertical, symbol rate 27500.",
            category = "Broadcast",
            timeAgo = "45m ago",
            isBreaking = false
        ),
        NewsAlert(
            id = "news_3",
            headline = "Continental Football Championship: Group Stage Draw Confirmed",
            fullStory = "The official draw in Abidjan pitted powerhouse squads into dramatic rivalries. Fans can watch all live pre-match coverage and studio analysis exclusively on ABASS Sports.",
            category = "Sports",
            timeAgo = "1h ago",
            isBreaking = false
        )
    )

    val satelliteParameters = listOf(
        SatelliteInfo(
            satelliteName = "Badr 7 (BADR-7)",
            orbitalPosition = "26.0° East",
            frequency = "11595 MHz",
            polarization = "Vertical (V)",
            symbolRate = "27500 Msps",
            fec = "5/6",
            modulation = "DVB-S2 / 8PSK (MPEG-4 / Full HD)",
            coverage = "West Africa, North Africa & Sahel"
        ),
        SatelliteInfo(
            satelliteName = "Eutelsat 16A",
            orbitalPosition = "16.0° East",
            frequency = "12562 MHz",
            polarization = "Horizontal (H)",
            symbolRate = "30000 Msps",
            fec = "3/4",
            modulation = "DVB-S2 / QPSK",
            coverage = "Sub-Saharan Africa & Central Africa"
        ),
        SatelliteInfo(
            satelliteName = "Nilesat 201 / 301",
            orbitalPosition = "7.0° West",
            frequency = "12149 MHz",
            polarization = "Horizontal (H)",
            symbolRate = "27500 Msps",
            fec = "5/6",
            modulation = "DVB-S2",
            coverage = "North Africa, Middle East & Southern Mediterranean"
        )
    )
}
